/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view.login;

import edu.logic.ButtonHandler;
import edu.view.FindDocumentButton;
import edu.view.SendButton;
import edu.view.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author DavidCamilo
 */
public class LoginFrame extends JFrame{
    
    private JTextField txtUser = new JTextField();
    private JPasswordField pssPassword = new JPasswordField();
    
    public LoginFrame(int width, int height, String title, int posX, int posY){
        this.setBounds(posX, posY, width, height);
        this.setTitle(title);
        
        JPanel pnlFrame = new JPanel(new BorderLayout(20, 20));
        JPanel pnlForm = new JPanel(new BorderLayout(20, 20));
        JPanel pnlFormFields = new JPanel(new SpringLayout());
        
        pnlFormFields.add(new JLabel("Usuario:", JLabel.TRAILING));
        pnlFormFields.add(txtUser);
        pnlFormFields.add(new JLabel("Password:", JLabel.TRAILING));
        pnlFormFields.add(pssPassword);
        
        SpringUtilities.makeCompactGrid(pnlFormFields, 2, 2, 0, 0, 10, 10);
        
        JPanel pnlSendButton = new JPanel();
        JButton btnSend = new LoginButton("Aceptar", this);
        btnSend.addActionListener(new ButtonHandler());
        btnSend.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlSendButton.add(btnSend);
        
        pnlForm.add(pnlFormFields, BorderLayout.NORTH);
        pnlForm.add(pnlSendButton, BorderLayout.CENTER);
        
        pnlFrame.add(pnlForm, BorderLayout.CENTER);
        pnlFrame.add(new JPanel(), BorderLayout.WEST);
        pnlFrame.add(new JPanel(), BorderLayout.EAST);
        pnlFrame.add(new JPanel(), BorderLayout.NORTH);
        pnlFrame.add(new JPanel(), BorderLayout.SOUTH);
        
        this.add(pnlFrame);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public String getUser(){
        return txtUser.getText();
    }
    
    public char[] getPass(){
        return pssPassword.getPassword();
    }
}
