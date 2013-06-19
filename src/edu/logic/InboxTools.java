package edu.logic;

import java.util.ArrayDeque;
import java.util.Date;

/**
 *
 * @author david
 */
public class InboxTools {

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
        
        return inbox;
    }
}
