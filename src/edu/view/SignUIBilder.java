/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.UIBuilder;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author DavidCamilo
 */
public class SignUIBilder extends UIBuilder{

    private JPasswordField txtPass;
    private JTable tblCertList;
    
    @Override
    public void addUIControls() {
        panelUI = new JPanel(new BorderLayout(20, 20));

        JPanel pnlForm = new JPanel(new BorderLayout(10,10));
        JPanel pnlTop = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel pnlFindDocument = new JPanel(new BorderLayout(10, 0));
        JPanel pnlPass = new JPanel(new BorderLayout(10, 0));
        JPanel pnlCert = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        
        JLabel lblDocument = new JLabel("Documento a Firmar");
        JButton btnDocument = new FindDocumentButton("Buscar Documento");
        pnlFindDocument.add(lblDocument, BorderLayout.WEST);
        pnlFindDocument.add(btnDocument, BorderLayout.EAST);
        
        JLabel lblPass = new JLabel("Parafrase:");
        txtPass = new JPasswordField();
        pnlPass.add(lblPass, BorderLayout.WEST);
        pnlPass.add(txtPass, BorderLayout.CENTER);
        
        JLabel lblCert = new JLabel("Seseleccionar certificado para firmar");
        JButton bntCert = new CertRequestButton("Solicitar certificado");
        pnlCert.add(lblCert);
        pnlCert.add(bntCert);
        
        pnlTop.add(pnlFindDocument);
        pnlTop.add(pnlPass);
        pnlTop.add(pnlCert);
        
        String[] columnNames = {"Nombre", "Fecha"};
        Object[][] data = {
            {"Certificado 1", "1 Ago/2012"},
            {"Certificado 1", "1 Ago/2012"},
            {"Certificado 1", "1 Ago/2012"},
            {"Certificado 1", "1 Ago/2012"}
        };
        
        tblCertList = new JTable(data, columnNames);
        
        JButton btnSignSave = new SignSaveButton("Firmar y Guardar");
        JButton btnSignSend = new SignSendButton("Firmar y Enviar");
        pnlButtons.add(btnSignSave);
        pnlButtons.add(btnSignSend);
        
        pnlForm.add(pnlTop,BorderLayout.NORTH);
        pnlForm.add(tblCertList,BorderLayout.CENTER);
        pnlForm.add(pnlButtons,BorderLayout.SOUTH);
        
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
