/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.gui;

import edu.logic.tools.DocumentHandle;
import edu.logic.tools.ZipTools;
import edu.logic.pki.ExtFileSignerCreator;
import edu.logic.pki.KeyStoreTools;
import edu.api.SignerInterface;
import edu.api.SignerCreator;
import edu.view.FrameClient;
import edu.api.ws.FileServer;
import edu.logic.tools.PropertiesTool;
import edu.view.SignUIBilder;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.ArrayDeque;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;
/**
 *
 * @author DavidCamilo
 */
public class Mediator {
    
    private FrameClient frameClient;
    private File documentToSign;
    private DocumentHandle documentSigned;
    private SignerInterface signer;
    private ZipTools zipTools;
    private String signedPack, fileName, directory, status;
    private InputStream inputStream;
    private URL url;
    private QName qName;
    private Service service;
    private FileServer fileServer;
    private BindingProvider bindingProvider;
    private SOAPBinding soapBinding;
    private PropertiesTool properties;
    private KeyStoreTools kst = new KeyStoreTools("/home/david/prueba/ks", "password");
    private SignerCreator signerCreator = new ExtFileSignerCreator();
    private SignUIBilder sBuilder;
    
    /**
     *
     * @param frame
     */
    public void registerFrameClient(FrameClient frame){
        frameClient = frame;
    }
    /**
     *
     * @param builder
     */
    public void registerSigneUIBuilder(SignUIBilder builder){
        sBuilder = builder;
    }
    
    /**
     *
     * @return
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
        
        boolean created = (outFile.length() > 0L);
        
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
