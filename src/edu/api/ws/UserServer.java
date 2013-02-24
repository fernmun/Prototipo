/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api.ws;

/**
 *
 * @author lmparra
 */

import java.util.HashMap;
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
public interface UserServer{
    //Get User data
    /**
     *
     * @param uid
     * @param pass
     * @return
     */
    @WebMethod HashMap<String, Object> getUserData(int uid, String pass);
    /**
     *
     * @param userName
     * @param pass
     * @return
     */
    @WebMethod String[] getUserDataByUserName(String userName, String pass);

}
