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
@WebService
@SOAPBinding(style = Style.RPC)
public interface CertificateServer{
    //Get DN (Distinguished Name)
    @WebMethod String getDN(String uid);

    //download a digital certificate from server
    @WebMethod byte[] downloadCertificate(String name);

    //update a digital certificate to server
    @WebMethod String uploadCertificate(byte[] data);

}
