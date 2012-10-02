/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.UIBuilder;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

/**
 *
 * @author DavidCamilo
 */
public class SendUIBuilder extends UIBuilder{

    
    private JTextArea txtMessage;
    
    @Override
    public void addUIControls() {
        panelUI = new JPanel(new BorderLayout(20, 20));
        JPanel pnlForm = new JPanel(new BorderLayout(10, 10));
        JPanel pnlFormFields = new JPanel(new SpringLayout());
        
        
        pnlFormFields.add(new JLabel("Documento firmado:"));
        pnlFormFields.add(new FindDocumentButton("Buscar documento"));
        
        pnlFormFields.add(new JLabel("Destinatario:"));
        pnlFormFields.add(new FindUserButton("Escoger Usuario:"));
        
        txtMessage = new JTextArea();
        JScrollPane jspMessage = new JScrollPane(txtMessage);
        pnlFormFields.add(new JLabel("Mensaje:"));
        pnlFormFields.add(jspMessage);
        
        SpringUtilities.makeCompactGrid(pnlFormFields,
                                        3, 2, //rows, cols
                                        6, 6,        //initX, initY
                                        6, 6);       //xPad, yPad
        
        
        
        JPanel pnlSendButton = new JPanel();
        SendButton btnSend = new SendButton("Enviar");
        btnSend.setAlignmentX(Component.RIGHT_ALIGNMENT);
        pnlSendButton.add(btnSend);
        
        pnlForm.add(pnlFormFields, BorderLayout.CENTER);
        pnlForm.add(pnlSendButton, BorderLayout.SOUTH);
        
        panelUI.add(new JPanel(), BorderLayout.NORTH);
        panelUI.add(new JPanel(), BorderLayout.WEST);
        panelUI.add(new JPanel(), BorderLayout.EAST);
        panelUI.add(new JPanel(), BorderLayout.SOUTH);
        panelUI.add(pnlForm, BorderLayout.CENTER);
    }

    @Override
    public void initialize() {
        System.out.println("Not supported yet.");
    }
    
}
