package edu.logic.gui;

import edu.api.ServicesProviderInterface;
import edu.api.SignVerifier;
import edu.api.SignerCreator;
import edu.api.SignerInterface;
import edu.logic.Document;
import edu.logic.ExtSignDocumentCreator;
import edu.logic.SignDocument;
import edu.logic.WebServicesProvider;
import edu.ws.FileServer;
import edu.logic.pki.ExtFileSignerCreator;
import edu.logic.pki.ExtSignVerifierCreator;
import edu.logic.pki.KeyStoreTools;
import edu.logic.tools.DocumentHandle;
import edu.logic.tools.PropertiesTool;
import edu.logic.tools.StringCipher;
import edu.view.FrameClient;
import edu.view.InboxUIBuilder;
import edu.view.SendUIBuilder;
import edu.view.SettingsUIBuilder;
import edu.view.SignUIBuilder;
import edu.view.VerifyUIBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
    private PropertiesTool settings;
    private KeyStoreTools kst;
    private SignUIBuilder sBuilder;
    private SendUIBuilder eBuilder;
    private InboxUIBuilder iBuilder;
    private VerifyUIBuilder vBuilder;
    private SettingsUIBuilder cBuilder;
    private ServicesProviderInterface provider;
    private int userToSend, processToSend;

    public Mediator() {
        try {
            settings = new PropertiesTool("settings.properties");
        } catch (IOException ex) {
            Logger.getLogger(Mediator.class.getName()).log(Level.SEVERE, null, ex);
        }
        provider = WebServicesProvider.getWebServicesProvider();
    }

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
    
    public void registerSendUIBuilder(SendUIBuilder builder){
        eBuilder = builder;
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
    
    public void registerVerifyUIBuilder(VerifyUIBuilder builder){
        vBuilder = builder;
    }
    
    /**
     *
     * Registers Inbox builder t
     *
     * @param builder
     *        {@link InboxUIBuilder}
     */
    public void registerSettingsUIBuilder(SettingsUIBuilder builder){
        cBuilder = builder;
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
    public boolean signDocument() throws IOException{
        String name = documentToSign.getPath();
        String ext =  name.substring(name.lastIndexOf(".")+1);

        String alias = sBuilder.getSelectedAlias();
        PrivateKey pk = (PrivateKey) kst.getKey(alias, sBuilder.getPassword());
        if(pk == null){
            JOptionPane.showMessageDialog(frameClient, "Error al firmar, por favor revise su pass");
            return false;
        }
        String pass = settings.getProperty("keystore.password");
        if(pass != null){
            try {
                pass = StringCipher.decrypt(pass);
            } catch (Exception ex) {
                Logger.getLogger(Mediator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        kst = new KeyStoreTools(settings.getProperty("keystore.path"), pass);
        Certificate[] chain = kst.getCertificateChain(alias);

        SignerCreator signerCreator = new ExtFileSignerCreator();
        
        SignerInterface signer = signerCreator.getSigner(ext);

        File outFile = signer.sign(documentToSign, pk, chain[0]);
        
        boolean created = (outFile != null && outFile.length() > 0L);

        if(created) {
            documentToSign = outFile;
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
        boolean sucess = false;
        if(userToSend > 0){
            sucess = provider.sendDocumentToUser(documentToSign, eBuilder.getMessage(), userToSend);
        } else if(processToSend > 0){
            sucess = provider.sendDocumentToProcess(documentToSign, eBuilder.getMessage(), processToSend);
        }
        return sucess;
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

        ArrayDeque<Document> documents = iBuilder.getSelectedDocuments();
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
        byte[] buffer = new byte[4096]; int bytesRead = 0; 
        System.out.print("Downloading " + downloadedFileName); 
        while ((bytesRead = is.read(buffer)) != -1) { 
            System.out.print(".");
            // Progress bar :)
            fos.write(buffer,0,bytesRead); 
        } System.out.println("done!");
        // Close destination stream fos.close();
        // Close URL stream
        is.close();
    }
    
    public boolean verifyDocument(){
        String fileToVerify = vBuilder.getFileToVerify();
        String ext =  fileToVerify.substring(fileToVerify.lastIndexOf(".")+1);
        SignDocument document = new ExtSignDocumentCreator().getSignDocument(ext);
        Certificate cert = null;
        if(document.isSigned()){
            cert = document.getSignatureCertificate();
        }
        String msj = "Documento no valido";
        
        SignVerifier verifier = new ExtSignVerifierCreator().getSignVerifier(ext);
        if(verifier.verify(new File(fileToVerify))){
            msj = "Firma valida";
            if(cert != null){
                
            }
        }
        JOptionPane.showMessageDialog(frameClient, msj);
        return false;
    }
    
    public void saveSettings(){
        HashMap<String, String> settingsTmp = cBuilder.getSettings();
        Iterator it = settingsTmp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> setting = (Map.Entry)it.next();
            settings.setProperty(setting.getKey(), setting.getValue());
        }
        try {
            settings.store();
        } catch (IOException ex) {
            Logger.getLogger(Mediator.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(settings);
    }

    public void setUserToSend(int userToSend) {
        this.userToSend = userToSend;
    }

    public void setProcessToSend(int processToSend) {
        this.processToSend = processToSend;
    }

    public void readDocuments(){
    }
    
    public void unreadDocuments(){
    }
    
}
