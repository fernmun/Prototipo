/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api.ws;

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
public interface CertificateServer{
    //Get DN (Distinguished Name)
    /**
     *
     * @param uid
     * @return
     */
    @WebMethod String getDN(String uid);

    //download a digital certificate from server
    /**
     *
     * @param name
     * @return
     */
    @WebMethod byte[] downloadCertificate(String name);

    //update a digital certificate to server
    /**
     *
     * @param data
     * @return
     */
    @WebMethod String uploadCertificate(byte[] data);

}
