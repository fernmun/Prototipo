package edu.api.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

/**
 *
 * This interface represents web services methods needed to download and upload 
 * digital certificates remotely through MTOM protocol for efficiently sending 
 * binary data to and from Web services.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
@WebService
@SOAPBinding(style = Style.RPC)
public interface CertificateServer{
    
    /**
     *
     * Get the DN (Distinguished Name) by user
     * 
     * @param uid
     * @return
     */
    @WebMethod String getDN(String uid);

    /**
     *
     * Download digital certificate from server
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
