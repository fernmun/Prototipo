/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import edu.logic.CommandExecutor;
import edu.logic.KeyStoreTools;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

/**
 *
 * @author lmparra
 */
public class Prototipo {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws Exception {
    // TODO code application logic here
      CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

        FileInputStream fis = new FileInputStream("signedcert.pem");

        Certificate cert = certFactory.generateCertificate(fis);
        fis.close();
        
//        System.out.println(cert);
        
//        KeyTools keyTools = new KeyTools();
//        KeyPair pair = keyTools.generateKeyPair();
//        System.out.println(pair.getPrivate());
//        System.out.println(pair.getPublic());
//        Certificate certificate = keyTools.generateSelfSignedCertificate(pair, "CN=dnova, L=Bogotá, ST=Cundinamarca ,C=CO, O=Test");
//        System.out.println(certificate);
//        keyTools.generateCSR("CN=dnova, L=Bogotá, ST=Cundinamarca ,C=CO, O=Test",pair.getPrivate(),certificate.getPublicKey(), "newreq.pem");
//        System.out.println(certificate.getPublicKey());
//        System.out.println(pair.getPublic());
        KeyStoreTools kstPrueba = new KeyStoreTools("ksPrueba", "password");
//        kstPrueba.addKey("testkey", pair.getPrivate(), "password", new Certificate[]{certificate});
//        kstPrueba.addCertificate("testkey", cert, "password");
//        keyTools.generateCSR("CN=Test, L=London, ST=Stale ,C=GB, O=Test",pair.getPrivate(),pair.getPublic(), "test.csr");
//        Certificate cerGoogle = kstPrueba.getCertificate("google");
        kstPrueba.testKeyStore(cert);
//        System.out.println(cerGoogle);
  }
}
