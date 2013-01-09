/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import edu.view.FrameClient;
import edu.ws.FileServer;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
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
    private Document documentSigned;
    private Signer signer;
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
    
    public void registerFrameClient(FrameClient frame){
        frameClient = frame;
    }
    
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
    
    public boolean signDocument(){
        signer = new Signer();
        Vector files = signer.sign(documentToSign, null);
        String name = documentToSign.getPath();
        name = name.substring(0, name.lastIndexOf(".")+1)+"zip";
        zipTools = new ZipTools();
        signedPack = zipTools.compressFiles(files, name);
        JOptionPane.showMessageDialog(frameClient, "Documento Firmado");
        return !signedPack.isEmpty();
    }
    
    public boolean sendDocument() throws Exception{
        properties = new PropertiesTool("web-services.properties");
        
        fileName = properties.getProperty("ws.up_file");
        directory = properties.getProperty("ws.up_directory");
        
        url = new URL(properties.getProperty("ws.file_request"));
        qName = new QName(properties.getProperty("ws.file_request.qname"), properties.getProperty("ws.file_request.service"));
        service = Service.create(url, qName);
        fileServer = service.getPort(FileServer.class);
        
        documentSigned = new Document(fileName, directory);
        
        //Enable MTOM in client
        bindingProvider = (BindingProvider) fileServer;
        soapBinding = (SOAPBinding) bindingProvider.getBinding();
        soapBinding.setMTOMEnabled(true);
        
        status = fileServer.uploadFile(documentSigned.readFile());
        
        return false;
    }
    
    public boolean signSendDocument() throws Exception{
        if(signDocument()){
            return sendDocument();
        }
        else{
            return false;
        }
    }
}
