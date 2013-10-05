package edu.logic;

import edu.view.login.LoginButton;
import edu.ws.FileServer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 *
 * @author david
 */
public class InboxTools {

    
    private FileServer fileServer;
    private URL url;
    private Service service;
    
    /**
     * 
     */
    public InboxTools() {
    }
    
    /**
     *
     * @return
     */
    public ArrayDeque<Document> getInboxDocuments(){
        long created = 1290492000000L;
        long updated = 1370062800000L;
        ArrayDeque<Document> inbox = new ArrayDeque<Document>();
        inbox.add(new Document(1, "Documento 1", "http://www.elespectador.com/files/001116f2f3226b73e6d1a5191363139d.pdf", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 2", "http://elespectador.s3.amazonaws.com/files/005e5f1bc965d89fa45e1a20c2fb4412.pdf", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 3", "http://elespectador.s3.amazonaws.com/files/00c92bbb3552595cee3b4535796db9f4.pdf", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 4", "http://elespectador.s3.amazonaws.com/files/0167f60bb2154aea6d6bc001f018b6a8.pdf", new Date(created), new Date(updated)));

//        inbox = getUserInboxFiles();
        return inbox;
    }
    
    /**
     *
     * @return
     */
    public ArrayDeque<Document> getReadDocuments(){
        long created = 1290592000000L;
        long updated = 1370562800000L;
        ArrayDeque<Document> inbox = new ArrayDeque<Document>();
        inbox.add(new Document(1, "Documento 5", "http://www.elespectador.com/files/001116f2f3226b73e6d1a5191363139d.pdf?o=r", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 6", "http://elespectador.s3.amazonaws.com/files/005e5f1bc965d89fa45e1a20c2fb4412.pdf?o=r", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 7", "http://elespectador.s3.amazonaws.com/files/00c92bbb3552595cee3b4535796db9f4.pdf?o=r", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 8", "http://elespectador.s3.amazonaws.com/files/0167f60bb2154aea6d6bc001f018b6a8.pdf?o=r", new Date(created), new Date(updated)));

//        inbox = getUserInboxFiles();
        return inbox;
    }
    
    public ArrayDeque<Document> getSentDocuments(){
        long created = 1289592000000L;
        long updated = 1369562800000L;
        ArrayDeque<Document> inbox = new ArrayDeque<Document>();
        inbox.add(new Document(1, "Documento 9", "http://www.elespectador.com/files/001116f2f3226b73e6d1a5191363139d.pdf?o=s", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 10", "http://elespectador.s3.amazonaws.com/files/005e5f1bc965d89fa45e1a20c2fb4412.pdf?o=s", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 11", "http://elespectador.s3.amazonaws.com/files/00c92bbb3552595cee3b4535796db9f4.pdf?o=s", new Date(created), new Date(updated)));
        created += 24*3600*1000;
        updated += 24*3600*1000;
        inbox.add(new Document(1, "Documento 12", "http://elespectador.s3.amazonaws.com/files/0167f60bb2154aea6d6bc001f018b6a8.pdf?o=s", new Date(created), new Date(updated)));

//        inbox = getUserInboxFiles();
        return inbox;
    }
    
    private ArrayDeque<Document> getUserInboxFiles(){
    
        ArrayDeque<Document> documents = new ArrayDeque<Document>();
        
        try {
            User user = User.getUser();
            
            url = new URL("http://192.168.43.104:8080/ws_prototipo/FileServerImpl?wsdl");
            QName qname = new QName("http://ws.edu/", "FSImpl");

            service = Service.create(url, qname);
            fileServer = service.getPort(FileServer.class);

            //enable MTOM in client
            BindingProvider bp = (BindingProvider) fileServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);

            int[] status = fileServer.getUserFiles(user.getUid());
            System.out.println(user.getUid());
            System.out.println(status.length);
            
            for (int idDocument : status) {
                String[] document = fileServer.getDocument(idDocument);
                Document doc = new Document(idDocument, document[1], "http://"+document[3]+"/"+document[1], new Date(), new Date());
                System.out.println("0"+document[0]);
                System.out.println("1"+document[1]);
                System.out.println("2"+document[2]);
                System.out.println("3"+document[3]);
//                Document doc = new Document(idDocument, document[1], document[2], new Date(document[4]), new Date(document[4]));
                documents.add(doc);
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
        return documents;
    }
}
