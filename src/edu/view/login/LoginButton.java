/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view.login;

import edu.api.CommandInterface;
import edu.logic.User;
import edu.view.FrameClient;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
        if(loginFrame.getUser().compareTo("admin") == 0 && new String(loginFrame.getPass()).compareTo("admin") == 0){
            user = User.getUser();
        }
        if(user == null){
            JOptionPane.showMessageDialog(loginFrame, "No se reconoce usuario", "Ingresar", JOptionPane.ERROR_MESSAGE);
            FrameClient frame = new FrameClient(800, 600, "Ventana de prueba", 200, 50, "algo");
            loginFrame.dispose();
        }
        else{
            FrameClient frame = new FrameClient(800, 600, "Ventana de prueba", 200, 50, "algo");
            loginFrame.dispose();
        }
    }
    
}
