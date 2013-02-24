/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.pki.KeyTools;
import edu.api.ws.CertificateServer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

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
        try {
            
            URL url = new URL("http://192.168.0.27:9999/ws/certificate?wsdl");
            QName qname = new QName("http://ws.edu/", "CertificateServerImplService");

            Service service = Service.create(url, qname);
            CertificateServer certificateServer = service.getPort(CertificateServer.class);
            
            //enable MTOM in client
            BindingProvider bp = (BindingProvider) certificateServer;
            SOAPBinding binding = (SOAPBinding) bp.getBinding();
            binding.setMTOMEnabled(true);
       
            String status = certificateServer.getDN("1");
            System.out.println(status);
            
            if(!status.isEmpty()){
                
                String distinguishedName = status;
                
                KeyTools keyTools = new KeyTools();
                KeyPair pair = keyTools.generateKeyPair();
                Certificate certificate = keyTools.generateSelfSignedCertificate(pair, distinguishedName);
                String csr = keyTools.generateCSR(distinguishedName,pair.getPrivate(),certificate.getPublicKey());
                System.out.println(csr);
            }
            else{
                JOptionPane.showMessageDialog(this, "No es posible validar su solicitud, por favlor verifique los datos suministrados.", "Solucitud de certificado", JOptionPane.ERROR_MESSAGE);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(CertRequestButton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}