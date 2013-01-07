/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.CommandInterface;
import edu.logic.KeyTools;
import java.security.KeyPair;
import java.security.cert.Certificate;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author DavidCamilo
 */
public class CertRequestButton extends JButton implements CommandInterface{

    CertRequestButton(String text) {
        super(text);
    }

    @Override
    public void processEvent() {
        boolean isAuthorized = true;
        if(isAuthorized){
            
            String distinguishedName = "CN=dnova, L=Bogotá, ST=Cundinamarca ,C=CO, O=Test";
            
            KeyTools keyTools = new KeyTools();
            KeyPair pair = keyTools.generateKeyPair();
            Certificate certificate = keyTools.generateSelfSignedCertificate(pair, distinguishedName);
            String csr = keyTools.generateCSR(distinguishedName,pair.getPrivate(),certificate.getPublicKey());
            System.out.println(csr);
        }
        else{
            JOptionPane.showMessageDialog(this, "No es posible validar su solicitud, por favlor verifique los datos suministrados.", "Solucitud de certificado", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}