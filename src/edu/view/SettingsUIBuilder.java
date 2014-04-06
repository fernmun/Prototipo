package edu.view;

import edu.api.gui.UIBuilder;
import edu.logic.gui.ButtonHandler;
import edu.logic.gui.Mediator;
import edu.logic.tools.PropertiesTool;
import edu.logic.tools.StringCipher;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
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
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class SettingsUIBuilder extends UIBuilder{
    
    private JTextField txtKeystore, txtDownloads, txtPassword, txtTrustKeystore,txtTrustPassword;
    private PropertiesTool properties;
    /**
     *
     * @param mediator
     */
    public SettingsUIBuilder(Mediator mediator){
        super(mediator);
        mediator.registerSettingsUIBuilder(this);
        try {
            properties = new PropertiesTool("settings.properties");
        } catch (IOException ex) {
            Logger.getLogger(SettingsUIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addUIControls() {
        
        panelUI = new JPanel(new BorderLayout(20, 20));
        JPanel pnlForm = new JPanel(new BorderLayout(10, 10));
        JPanel pnlFormFields = new JPanel(new SpringLayout());
        txtKeystore = new JTextField();
        txtTrustKeystore = new JTextField();
        txtDownloads = new JTextField();
        txtPassword = new JPasswordField();
        txtTrustPassword = new JPasswordField();
        ActionListener listener = new ButtonHandler();
        
        try {
            txtKeystore.setText(properties.getProperty("keystore.path"));
            txtTrustKeystore.setText(properties.getProperty("trustkeystore.path"));
            txtDownloads.setText(properties.getProperty("downloads.path"));
            String pass  = properties.getProperty("keystore.password");
            if(pass != null){
                txtPassword.setText(StringCipher.decrypt(pass));
            }
            pass  = properties.getProperty("trustkeystore.password");
            if(pass != null){
                txtTrustPassword.setText(StringCipher.decrypt(pass));
            }
        } catch (Exception ex) {
            Logger.getLogger(SettingsUIBuilder.class.getName()).log(Level.SEVERE, null, ex);
        } 
        pnlFormFields.add(new JLabel("Ruta al Keystore:", JLabel.TRAILING));
        pnlFormFields.add(txtKeystore);
        JButton btnKeystore = new BrowserButton("...", txtKeystore, JFileChooser.FILES_ONLY);
        btnKeystore.addActionListener(listener);
        pnlFormFields.add(btnKeystore);
        
        pnlFormFields.add(new JLabel("Contraseña del Keystore:", JLabel.TRAILING));
        pnlFormFields.add(txtPassword);
        JLabel lblPassword = new JLabel();
        pnlFormFields.add(lblPassword);
        
        pnlFormFields.add(new JLabel("Ruta de Descargas:", JLabel.TRAILING));
        pnlFormFields.add(txtDownloads);
        JButton btnDownloads = new BrowserButton("...", txtDownloads, JFileChooser.DIRECTORIES_ONLY);
        btnDownloads.addActionListener(listener);
        pnlFormFields.add(btnDownloads);
        
        pnlFormFields.add(new JLabel("Ruta al Keystore de confianza:", JLabel.TRAILING));
        pnlFormFields.add(txtTrustKeystore);
        JButton btnTrustKeystore = new BrowserButton("...", txtTrustKeystore, JFileChooser.FILES_ONLY);
        btnTrustKeystore.addActionListener(listener);
        pnlFormFields.add(btnTrustKeystore);
        
        pnlFormFields.add(new JLabel("Contraseña del Keystore:", JLabel.TRAILING));
        pnlFormFields.add(txtTrustPassword);
        JLabel lblTrustPassword = new JLabel();
        pnlFormFields.add(lblTrustPassword);
        
        SpringUtilities.makeCompactGrid(pnlFormFields, 5, 3, 0, 0, 10, 10);
        
        JPanel pnlSendButton = new JPanel();
        SettingsButton btnSend = new SettingsButton("Guardar", mediatorUI);
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
        System.out.println("Not supported yet.");
    }
    
    public HashMap getSettings(){
        HashMap<String, String> settings= new HashMap<String, String>();
       
        settings.put("keystore.path", txtKeystore.getText());
        settings.put("trustkeystore.path", txtTrustKeystore.getText());
        settings.put("downloads.path", txtDownloads.getText());
        String pass  = txtPassword.getText();
        if(pass != null){
            try {
                settings.put("keystore.password", StringCipher.encrypt(pass));
            } catch (Exception ex) {
                Logger.getLogger(SettingsUIBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        pass  = txtTrustPassword.getText();
        if(pass != null){
            try {
                settings.put("trustkeystore.password", StringCipher.encrypt(pass));
            } catch (Exception ex) {
                Logger.getLogger(SettingsUIBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return settings;
    }
    
}
