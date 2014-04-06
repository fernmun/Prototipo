package edu.view;

import edu.api.gui.UIBuilder;
import edu.logic.gui.ButtonHandler;
import edu.logic.gui.Mediator;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SendUIBuilder extends UIBuilder{

    
    private JTextArea txtMessage;
    
    /**
     *
     * @param mediator
     */
    public SendUIBuilder(Mediator mediator){
        super(mediator);
    } 
    
    /**
     *
     */
    @Override
    public void addUIControls() {
        
        ButtonHandler handler = new ButtonHandler();
        
        panelUI = new JPanel(new BorderLayout(20, 20));
        JPanel pnlForm = new JPanel(new BorderLayout(10, 10));
        JPanel pnlFormFields = new JPanel(new SpringLayout());
        
        
        pnlFormFields.add(new JLabel("Documento firmado:", JLabel.TRAILING));
        JButton btnFindDocument = new FindDocumentButton("Buscar documento", mediatorUI);
        btnFindDocument.addActionListener(handler);
        pnlFormFields.add(btnFindDocument);
        
        pnlFormFields.add(new JLabel("Destinatario:", JLabel.TRAILING));
        JButton btnChooseUser = new FindUserButton("Escoger Usuario", mediatorUI);
        btnChooseUser.addActionListener(handler);
        pnlFormFields.add(btnChooseUser);
        
        txtMessage = new JTextArea();
        JScrollPane jspMessage = new JScrollPane(txtMessage);
        pnlFormFields.add(new JLabel("Mensaje:", JLabel.TRAILING));
        pnlFormFields.add(jspMessage);
        
        SpringUtilities.makeCompactGrid(pnlFormFields, 3, 2, 0, 0, 10, 10);
        
        
        
        JPanel pnlSendButton = new JPanel();
        JButton btnSend = new SendButton("Enviar", mediatorUI);
        btnSend.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnSend.addActionListener(handler);
        pnlSendButton.add(btnSend);
        
        pnlForm.add(pnlFormFields, BorderLayout.CENTER);
        pnlForm.add(pnlSendButton, BorderLayout.SOUTH);
        
        panelUI.add(new JPanel(), BorderLayout.NORTH);
        panelUI.add(new JPanel(), BorderLayout.WEST);
        panelUI.add(new JPanel(), BorderLayout.EAST);
        panelUI.add(new JPanel(), BorderLayout.SOUTH);
        panelUI.add(pnlForm, BorderLayout.CENTER);
    }

    public String getMessage() {
        return txtMessage.getText();
    }

    /**
     *
     */
    @Override
    public void initialize() {
        System.out.println("Not supported yet.");
    }
    
}
