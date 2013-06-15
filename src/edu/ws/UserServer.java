package edu.ws;

import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
/**
 *
 * <code>UserServer</code> interface is an abstraction of User web service over JAX-WS
 * to get all information about a specific user.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
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
    @WebMethod HashMap<String, Object> getUserDataByUid(int uid, String pass);
    /**
     *
     * @param userName
     * @param pass
     * @return
     */
    @WebMethod String[] getUserDataByUserName(String userName, String pass);

}
