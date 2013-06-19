package edu.logic.gui;

import edu.api.SignerCreator;
import edu.api.SignerInterface;
import edu.logic.Document;
import edu.ws.FileServer;
import edu.logic.pki.ExtFileSignerCreator;
import edu.logic.pki.KeyStoreTools;
import edu.logic.tools.DocumentHandle;
import edu.logic.tools.PropertiesTool;
import edu.view.FrameClient;
import edu.view.InboxUIBuilder;
import edu.view.SignUIBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
/**
 *
 * <code>Mediator</code> class is a concrete mediator that coordinates communication
 * between <i>Colleague</i> objects.
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class Mediator {

    private FrameClient frameClient;
    private File documentToSign;
    private DocumentHandle documentSigned;
    private SignerInterface signer;
    private String fileName, directory, status;
    private URL url;
    private QName qName;
    private Service service;
    private FileServer fileServer;
    private BindingProvider bindingProvider;
    private SOAPBinding soapBinding;
    private PropertiesTool properties;
    private KeyStoreTools kst = new KeyStoreTools("/home/david/prueba/ks", "password");
    private SignerCreator signerCreator = new ExtFileSignerCreator();
    private SignUIBuilder sBuilder;
    private InboxUIBuilder iBuilder;

    /**
     *
     * Registers frame client of the application to use it.
     *
     * @param frame
     *        {@link FrameClient} Application frame
     */
    public void registerFrameClient(FrameClient frame){
        frameClient = frame;
    }
    /**
     *
     * Registers signer builder to create the concrete signer depending of kind of
     * file to sign.
     *
     * @param builder
     *        {@link SignUIBuilder}
     */
    public void registerSignerUIBuilder(SignUIBuilder builder){
        sBuilder = builder;
    }
    
    /**
     *
     * Registers Inbox builder t
     * 
     * @param builder
     *        {@link InboxUIBuilder}
     */
    public void registerInboxUIBuilder(InboxUIBuilder builder){
        iBuilder = builder;
    }

    /**
     *
     * Create a <code>JFileChooser</code> to choose the right document to sign.
     *
     * @return {@link String} File document path
     */
    public String findDocument(){
        String strPath = "";
        JFileChooser jcfSelectorArchivo = new JFileChooser();
        int iSeleccion = jcfSelectorArchivo.showOpenDialog(frameClient);
        if(iSeleccion == JFileChooser.APPROVE_OPTION){
            documentToSign = jcfSelectorArchivo.getSelectedFile();
            strPath = documentToSign.getPath();
        }
        return strPath;
    }

    /**
     *
     * @return
     */
    public boolean signDocument(){
         String name = documentToSign.getPath();
        String ext =  name.substring(name.lastIndexOf(".")+1);

        String alias = sBuilder.getSelectedAlias();
        PrivateKey pk = (PrivateKey) kst.getKey(alias, sBuilder.getPassword());
        if(pk == null){
            JOptionPane.showMessageDialog(frameClient, "Error al firmar, por favor revise su pass");
            return false;
        }
        Certificate[] chain = kst.getCertificateChain(alias);

        signer = signerCreator.getSigner(ext);

        File outFile = signer.sign(documentToSign, pk, chain[0]);

        boolean created = (outFile != null && outFile.length() > 0L);

        if(created) {
            JOptionPane.showMessageDialog(frameClient, "Documento Firmado");
        }
        else {
            JOptionPane.showMessageDialog(frameClient, "Error al firmar el documento");
        }

        return created;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public boolean sendDocument() throws Exception{
        properties = new PropertiesTool("web-services.properties");

        fileName = properties.getProperty("ws.up_file");
        directory = properties.getProperty("ws.up_directory");

        url = new URL(properties.getProperty("ws.file_request"));
        qName = new QName(properties.getProperty("ws.file_request.qname"), properties.getProperty("ws.file_request.service"));
        service = Service.create(url, qName);
        fileServer = service.getPort(FileServer.class);

        documentSigned = new DocumentHandle(fileName, directory);

        //Enable MTOM in client
        bindingProvider = (BindingProvider) fileServer;
        soapBinding = (SOAPBinding) bindingProvider.getBinding();
        soapBinding.setMTOMEnabled(true);

        status = fileServer.uploadFile(documentSigned.readFile(), fileName);

        return false;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    public boolean signSendDocument() throws Exception{
        if(signDocument()){
            return sendDocument();
        }
        else{
            return false;
        }
    }
    
    public void downloadDocumets(){
        
        ArrayDeque<Document> documents = iBuilder.getSelectedInboxDocuments();
        int i = 0;
        for (Iterator<Document> it = documents.iterator(); it.hasNext();) {
            Document document = it.next();
            System.out.println(document.getUri());
            try {
                download(document.getUri(),"/home/david/down");
            } catch (IOException ex) {
                Logger.getLogger(edu.logic.gui.Mediator.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        i++;
    }
    private void download(String fileURL, String destinationDirectory) throws IOException { 
        // File name that is being downloaded 
        String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/")+1); 
        // Open connection to the file 
        URL url = new URL(fileURL); InputStream is = url.openStream(); 
        // Stream to the destionation file 
        FileOutputStream fos = new FileOutputStream(destinationDirectory + "/" + downloadedFileName); 
        // Read bytes from URL to the local file 
        byte[] buffer = new byte[4096]; int bytesRead = 0; System.out.print("Downloading " + downloadedFileName); while ((bytesRead = is.read(buffer)) != -1) { System.out.print(".");	
        // Progress bar :) 
        fos.write(buffer,0,bytesRead); } System.out.println("done!"); 
        // Close destination stream fos.close(); 
        // Close URL stream 
        is.close(); 
    }


}
