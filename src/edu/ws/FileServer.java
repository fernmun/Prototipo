/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ws;

/**
 *
 * @author lmparra
 */

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
/**
 *
 * @author lmparra
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface FileServer{

    //download a image from server
    /**
     *
     * @param name
     * @return
     */
    @WebMethod byte[] downloadFile(String name);

    //update image to server
    /**
     *
     * @param data
     * @return
     */
    @WebMethod String uploadFile(byte[] data);

}
