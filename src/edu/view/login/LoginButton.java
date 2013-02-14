/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view.login;

import edu.api.CommandInterface;
import edu.logic.User;
import edu.view.FrameClient;
import edu.api.ws.CertificateServer;
import edu.api.ws.UserServer;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

/**
 *
 * @author DavidCamilo
 */
public class LoginButton extends JButton implements CommandInterface{

    private LoginFrame loginFrame;
    
    public LoginButton(String text, LoginFrame frame){
        super(text);
        loginFrame = frame;
    }
    
    @Override
    public void processEvent() {
        
        User user = null;
        
        URL url;
        try {
            url = new URL("http://localhost:9999/ws/user?wsdl");
            QName qname = new QName("http://ws.edu/", "UserServerImplService");

            Service service = Service.create(url, qname);
            UserServer userServer = service.getPort(UserServer.class);

            //enable MTOM in client
            BindingProvider bp = (BindingProvider) userServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);

            String pass = new String(loginFrame.getPass());
            
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes(), 0, pass.length());
            String md5 = new BigInteger(1, digest.digest()).toString(16);
            String[] status = userServer.getUserDataByUserName(loginFrame.getUser(), md5);
            Hashtable<String, String> fields = new Hashtable<String, String>();
            for (int i = 0; i < status.length; i++) {
                String string = status[i];
                String[] field = string.split("::");
                fields.put(field[0], field[1]);
            }
            if(status.length > 0) {
                user = User.getUser();
                user.setUid(new Integer(fields.get("idUser")));
                user.setUserName(fields.get("userName"));
                user.setFirstName(fields.get("name"));
                user.setLastName(fields.get("lastName"));
                user.setProfileName(fields.get("profile_name"));
            }
        
        } catch (MalformedURLException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginButton.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(user == null){
            JOptionPane.showMessageDialog(loginFrame, "No se reconoce usuario", "Ingresar", JOptionPane.ERROR_MESSAGE);
//            FrameClient frame = new FrameClient(800, 600, "Ventana de prueba", 200, 50, "algo");
//            loginFrame.dispose();
        }
        else{
            FrameClient frame = new FrameClient(800, 600, "Ventana de prueba", 200, 50, user);
            loginFrame.dispose();
        }
    }
    
}
