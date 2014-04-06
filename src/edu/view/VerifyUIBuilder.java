/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.view;

import edu.api.gui.UIBuilder;
import edu.logic.gui.ButtonHandler;
import edu.logic.gui.Mediator;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author DavidCamilo
 */
public class VerifyUIBuilder extends UIBuilder{

    public VerifyUIBuilder(Mediator mediator) {
        super(mediator);
        mediator.registerVerifyUIBuilder(this);
    }

    @Override
    public void addUIControls() {
        
        panelUI = new JPanel(new BorderLayout(20, 20));
        JPanel pnlForm = new JPanel(new BorderLayout(10, 10));
        JPanel pnlFormFields = new JPanel(new SpringLayout());
        JTextField txtKeystore = new JTextField();
        ActionListener listener = new ButtonHandler();
        
        pnlFormFields.add(new JLabel("Archivo a verificar:", JLabel.TRAILING));
        pnlFormFields.add(txtKeystore);
        JButton btnKeystore = new BrowserButton("...", txtKeystore, JFileChooser.FILES_ONLY);
        btnKeystore.addActionListener(listener);
        pnlFormFields.add(btnKeystore);
        
        SpringUtilities.makeCompactGrid(pnlFormFields, 1, 3, 0, 0, 10, 10);
        
        
        
        JPanel pnlSendButton = new JPanel();
        VerifyButton btnSend = new VerifyButton("Verificar", mediatorUI);
        btnSend.addActionListener(listener);
        btnSend.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlSendButton.add(btnSend);
        
        pnlForm.add(pnlFormFields, BorderLayout.NORTH);
        pnlForm.add(pnlSendButton, BorderLayout.SOUTH);
        
        panelUI.add(new JPanel(), BorderLayout.NORTH);
        panelUI.add(new JPanel(), BorderLayout.WEST);
        panelUI.add(new JPanel(), BorderLayout.EAST);
        panelUI.add(new JPanel(), BorderLayout.SOUTH);
        panelUI.add(pnlForm, BorderLayout.CENTER);
    }

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getFileToVerify(){
        return "";
    }
}
