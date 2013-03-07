package edu.logic;

import edu.api.SignVerifier;
import edu.api.SignerInterface;
import edu.logic.pki.FileSignVerifier;
import edu.logic.pki.FileSigner;
import edu.logic.pki.KeyStoreTools;
import edu.logic.pki.KeyTools;
import edu.logic.tools.ZipTools;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 *
 * Start class to test.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class Prototipo {

  /**
   * @param args the command line arguments
   * @throws Exception  
   */
  public static void main(String[] args) throws Exception {
      
      SignerInterface test = new FileSigner();
      KeyTools keyTools = new KeyTools();
//        KeyPair pair = keyTools.generateKeyPair();
//        Certificate certificate = keyTools.generateSelfSignedCertificate(pair, "CN=David Nova, L=Bogotá, ST=Cundinamarca ,C=CO, O=Test");
//        Certificate[] chain = new Certificate[]{certificate};
//         PrivateKey pk = pair.getPrivate();
        
        
        
        KeyStoreTools kst = new KeyStoreTools("/home/david/prueba/ks", "password");
//        kst.addPrivateKey("testpk", pk, "password", chain);
        
        PrivateKey pk = (PrivateKey) kst.getKey("testpk", "password".toCharArray());
        Certificate[] chain = kst.getCertificateChain("testpk");
        
        
//        
        File toSign = new File("/home/david/prueba/amazon.pdf");
        
        File toSave = new File("/home/david/prueba/amazon_s.zip");
        ZipTools tools = new ZipTools();
        SignVerifier verifier = new FileSignVerifier();
        System.out.println(verifier.verify(toSave));
//        tools.uncompressFiles(toSave.getAbsolutePath(), "/home/david/prueba/b/");
//        test.sign(toSign, toSave, pk, chain);
//        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//        FileInputStream fis = new FileInputStream("/home/david/prueba/amazon.cer");
//
//        Certificate cert = certFactory.generateCertificate(fis);
//        fis.close();
//        System.out.print(cert.getPublicKey());
      
    // TODO code application logic here
//      CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

//        FileInputStream fis = new FileInputStream("signedcert.pem");

//        Certificate cert = certFactory.generateCertificate(fis);
//        fis.close();
        
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
//        KeyStoreTools kstPrueba = new KeyStoreTools("ksPrueba", "password");
//        kstPrueba.testKeyStore(null);
//        kstPrueba.addPrivateKey("testkey", pair.getPrivate(), "password", new Certificate[]{certificate});
//        kstPrueba.addCertificate("testkey", cert, "password");
//        keyTools.generateCSR("CN=Test, L=London, ST=Stale ,C=GB, O=Test",pair.getPrivate(),pair.getPublic(), "test.csr");
//        Certificate cerGoogle = kstPrueba.getCertificate("google");
//        kstPrueba.testKeyStore(cert);
//        System.out.println(cerGoogle);
  }
}
