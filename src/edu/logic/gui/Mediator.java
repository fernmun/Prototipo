package edu.logic.gui;

import edu.api.SignerCreator;
import edu.api.SignerInterface;
import edu.api.ws.FileServer;
import edu.logic.pki.ExtFileSignerCreator;
import edu.logic.pki.KeyStoreTools;
import edu.logic.tools.DocumentHandle;
import edu.logic.tools.PropertiesTool;
import edu.view.FrameClient;
import edu.view.SignUIBuilder;
import java.io.File;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
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
        
        status = fileServer.uploadFile(documentSigned.readFile());
        
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
}
