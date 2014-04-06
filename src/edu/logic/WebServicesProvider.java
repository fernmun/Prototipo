/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.logic;

import edu.api.ServicesProviderInterface;
import edu.logic.pki.KeyTools;
import edu.logic.tools.DocumentHandle;
import edu.logic.tools.PropertiesTool;
import edu.view.login.LoginButton;
import edu.ws.CertificateServer;
import edu.ws.FileServer;
import edu.ws.UserServer;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 *
 * @author DavidCamilo
 */
public class WebServicesProvider implements ServicesProviderInterface{

    private static WebServicesProvider wsp = new WebServicesProvider();
    private User user;
    private final String WS_PROPERTIES_PATH = "web-services.properties";
    
    private WebServicesProvider(){}
    
    public static WebServicesProvider getWebServicesProvider(){
        return wsp;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(); 
    }
    
    @Override
    public boolean authUser(String userName, char[] pass) {
        boolean sucess = false;
        try {
            URL url = new URL("http://localhost:8080/ws_prototipo/UserServerImpl?wsdl");
            QName qname = new QName("http://ws.edu/", "UserServerImplService");

            Service service = Service.create(url, qname);
            UserServer userServer = service.getPort(UserServer.class);

            //enable MTOM in client
            BindingProvider bp = (BindingProvider) userServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);
            
            MessageDigest digest = MessageDigest.getInstance("MD5");
            String srtPass = new String(pass);
            digest.update(srtPass.getBytes(), 0, srtPass.length());
            String md5 = new BigInteger(1, digest.digest()).toString(16);
            String[] status = userServer.getUserDataByUserName(userName, md5);
            HashMap<String, String> fields = new HashMap<String, String>();
            for (int i = 0; i < status.length; i++) {
                String string = status[i];
                String[] field = string.split("::");
                fields.put(field[0], field[1]);
            }
            if(status.length > 0) {
                user = User.getUser();
                user.setUid(new Integer(fields.get("idUser")));
                user.setUserName(fields.get("userName"));
                user.setFirstName(fields.get("name"));
                user.setLastName(fields.get("lastName"));
                user.setProfileName(fields.get("profile_name"));
                sucess = true;
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }

    @Override
    public User getUser() {
        return user;
    }
    
    @Override
    public String getUserDN() {
        String DN = "";
        try {
            URL url = new URL("http://192.168.0.27:9999/ws/certificate?wsdl");
            QName qname = new QName("http://ws.edu/", "CertificateServerImplService");

            Service service = Service.create(url, qname);
            CertificateServer certificateServer = service.getPort(CertificateServer.class);
            
            //enable MTOM in client
            BindingProvider bp = (BindingProvider) certificateServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);
       
            DN = certificateServer.getDN(""+user.getUid());
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(WebServicesProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DN;
    }

    @Override
    public boolean sendCSR(String CSR) {
        boolean sucess = false;
        String DN = user.getDistinguishedName();

        if(DN.isEmpty()){
            DN = getUserDN();
        }

        if(!DN.isEmpty()){

            KeyTools keyTools = new KeyTools();
            KeyPair pair = keyTools.generateKeyPair();
            Certificate certificate = keyTools.generateSelfSignedCertificate(pair, DN);
            String csr = keyTools.generateCSR(DN,pair.getPrivate(),certificate.getPublicKey());
            System.out.println(csr);
        }
        
        {
            sucess = true;
        }
        
        return sucess;
    }

    @Override
    public boolean sendDocumentToUser(File document, String message, int userId) {
        boolean sucess = false;
        try {
            PropertiesTool properties = new PropertiesTool(WS_PROPERTIES_PATH);
            
            String fileName = properties.getProperty("ws.up_file");
            String directory = properties.getProperty("ws.up_directory");
            
            URL url = new URL(properties.getProperty("ws.file_request"));
            QName qName = new QName(properties.getProperty("ws.file_request.qname"), properties.getProperty("ws.file_request.service"));
            Service service = Service.create(url, qName);
            FileServer fileServer = service.getPort(FileServer.class);
            
            DocumentHandle documentSigned = new DocumentHandle(fileName, directory);
            
            //Enable MTOM in client
            BindingProvider bindingProvider = (BindingProvider) fileServer;
            SOAPBinding soapBinding = (SOAPBinding) bindingProvider.getBinding();
            soapBinding.setMTOMEnabled(true);
            
            String status = fileServer.uploadFile(documentSigned.readFile(), fileName);
            sucess = true;
        } catch (IOException ex) {
            Logger.getLogger(WebServicesProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }
    
    @Override
    public boolean sendDocumentToProcess(File document, String message, int userId) {
        boolean sucess = false;
        try {
            PropertiesTool properties = new PropertiesTool(WS_PROPERTIES_PATH);
            
            String fileName = properties.getProperty("ws.up_file");
            String directory = properties.getProperty("ws.up_directory");
            
            URL url = new URL(properties.getProperty("ws.file_request"));
            QName qName = new QName(properties.getProperty("ws.file_request.qname"), properties.getProperty("ws.file_request.service"));
            Service service = Service.create(url, qName);
            FileServer fileServer = service.getPort(FileServer.class);
            
            DocumentHandle documentSigned = new DocumentHandle(fileName, directory);
            
            //Enable MTOM in client
            BindingProvider bindingProvider = (BindingProvider) fileServer;
            SOAPBinding soapBinding = (SOAPBinding) bindingProvider.getBinding();
            soapBinding.setMTOMEnabled(true);
            
            String status = fileServer.uploadFile(documentSigned.readFile(), fileName);
            sucess = true;
        } catch (IOException ex) {
            Logger.getLogger(WebServicesProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucess;
    }

    @Override
    public Document[] getInDocuments() {
        Document[] documents = null;
        try {
            User user = User.getUser();
            
            URL url = new URL("http://192.168.43.104:8080/ws_prototipo/FileServerImpl?wsdl");
            QName qname = new QName("http://ws.edu/", "FSImpl");

            Service service = Service.create(url, qname);
            FileServer fileServer = service.getPort(FileServer.class);

            //enable MTOM in client
            BindingProvider bp = (BindingProvider) fileServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);

            int[] status = fileServer.getUserFiles(user.getUid());
            System.out.println(user.getUid());
            System.out.println(status.length);
            documents = new Document[status.length];
            int i =0;
            for (int idDocument : status) {
                String[] document = fileServer.getDocument(idDocument);
                Document doc = new Document(idDocument, document[1], "http://"+document[3]+"/"+document[1], new Date(), new Date());
                System.out.println("0"+document[0]);
                System.out.println("1"+document[1]);
                System.out.println("2"+document[2]);
                System.out.println("3"+document[3]);
//                Document doc = new Document(idDocument, document[1], document[2], new Date(document[4]), new Date(document[4]));
                documents[i] = doc;
                i++;
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return documents;
    }

    @Override
    public Document[] getOutDocuments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Document[] getSentDocuments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean markAsReaded(Document document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean markAsUnreaded(Document document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean downloadDocument(Document document) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<Integer,String> getUsers() {
        HashMap<Integer,String> users = new HashMap<Integer, String>();
        users.put(new Integer(100), "UserName1");
        users.put(new Integer(101), "UserName2");
        users.put(new Integer(102), "UserName3");
        return users;
    }

    @Override
    public HashMap<Integer,String> getProcesses() {
        
        HashMap<Integer,String> users = new HashMap<Integer, String>();
        users.put(new Integer(100), "Convocatoria 1");
        users.put(new Integer(101), "Convocatoria 2");
        return users;
    }
    
}
