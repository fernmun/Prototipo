/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ws;

import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface FileServer{

    //Get a document
    /**
     *
     * @param idDocument Document id
     * @return
     */
    @WebMethod String[] getDocument(int idDocument);

    //Get all user documents
    /**
     *
     * @param uid User id
     * @return
     */
    @WebMethod int[] getUserFiles(int uid);

    //Set status of document
    /**
     *
     * @param idDocument Document id
     * @param state Document state. 1 as readed or 0 as unreaded
     * @return
     */
    @WebMethod boolean setFileState(int idDocument, int state);

    //download a image from server
    /**
     *
     * @param name File name
     * @return
     */
    @WebMethod byte[] downloadFile(String name);

    //update image to server
    /**
     *
     * @param data File bytes map to transmit
     * @param String File name
     * @return
     */
    @WebMethod String uploadFile(byte[] data, String name);

}
