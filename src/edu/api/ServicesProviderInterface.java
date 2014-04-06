/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.api;

import edu.logic.Document;
import edu.logic.User;
import java.io.File;
import java.util.ArrayDeque;
import java.util.HashMap;

/**
 *
 * @author DavidCamilo
 */
public interface ServicesProviderInterface {
    public boolean authUser(String userName, char[] pass);
    public String getUserDN();
    public User getUser();
    public boolean sendCSR(String CSR);
    public boolean sendDocumentToUser(File document, String message, int userId);
    public boolean sendDocumentToProcess(File document, String message, int processId);
    public Document[] getInDocuments();
    public Document[] getOutDocuments();
    public Document[] getSentDocuments();
    public boolean markAsReaded(Document document);
    public boolean markAsUnreaded(Document document);
    public boolean downloadDocument(Document document);
    public HashMap<Integer,String> getUsers();
    public HashMap<Integer,String> getProcesses();
}
