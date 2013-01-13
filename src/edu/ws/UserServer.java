/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ws;

/**
 *
 * @author lmparra
 */

import java.util.Hashtable;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface UserServer{
    //Get User data
    @WebMethod Hashtable<String, Object> getUserData(int uid, String pass);
    @WebMethod String[] getUserDataByUserName(String userName, String pass);

}
