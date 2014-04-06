package edu.view.login;

import edu.api.gui.CommandInterface;
import edu.logic.User;
import edu.logic.WebServicesProvider;
import edu.view.FrameClient;
import edu.ws.UserServer;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceProvider;
import javax.xml.ws.soap.SOAPBinding;

/**
 * <code>LoginButton</code> Class allow extends <code>JButton</code>
 * functionality to process login event
 * 
 * @author DavidCamilo
 */
public class LoginButton extends JButton implements CommandInterface{

    private LoginFrame loginFrame;
    
    /**
     *
     * @param text
     * @param frame
     */
    public LoginButton(String text, LoginFrame frame){
        super(text);
        loginFrame = frame;
    }
    
    /**
     *
     */
    @Override
    public void processEvent() {
        WebServicesProvider provider = WebServicesProvider.getWebServicesProvider();
        if(provider.authUser(loginFrame.getUser(), loginFrame.getPass())){
            new FrameClient(800, 600, "Ventana de prueba", 200, 50, provider.getUser());
            loginFrame.dispose();
        }
        else{
            JOptionPane.showMessageDialog(loginFrame, "No se reconoce usuario", "Ingresar", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
