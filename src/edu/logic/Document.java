/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.util.Date;

/**
 *
 * @author david
 */
public class Document {
    
    static final int READED_STATE = 1;
    static final int UNREADED_STATE = 0;
    
    static final int SIGNED_STATE = 1;
    static final int UNSIGNED_STATE = 0;
    
    private int idDocument;
    private String name;
    private String uri;
    private String state;
    private Date created;
    private Date updated;

    /**
     *
     * @param idDocument
     * @param name
     * @param uri
     * @param created
     * @param updated
     */
    public Document(int idDocument, String name, String uri, Date created, Date updated) {
        this.idDocument = idDocument;
        this.name = name;
        this.uri = uri;
        this.created = created;
        this.updated = updated;
    }

    /**
     *
     * @return
     */
    public int getDid() {
        return idDocument;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @return
     */
    public Date getCreated() {
        return created;
    }

    /**
     *
     * @return
     */
    public Date getUpdated() {
        return updated;
    }
    
}
