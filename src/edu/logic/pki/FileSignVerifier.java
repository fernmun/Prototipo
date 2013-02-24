/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.SignVerifier;
import edu.logic.tools.ZipTools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

/**
 *
 * @author dnova
 */
public class FileSignVerifier implements SignVerifier{

    private final String algorithm = "SHA1withRSA";
    private final String provider = "SunRsaSign";
    private final String certType = "X.509";
    
    public boolean verify(File file, File publicCert, File externalSign) {
        boolean valido=false;
        
        try{

            /* import encoded public key */
            CertificateFactory certFactory = CertificateFactory.getInstance(certType);
            FileInputStream fis = new FileInputStream(publicCert);

            Certificate cert = certFactory.generateCertificate(fis);
            fis.close();
            PublicKey pubKey = cert.getPublicKey();
            
            BASE64Encoder encoder=new BASE64Encoder();
            System.out.println("hhhhhhhhhhhh-");
            System.out.println(cert);
            String psB64Certificate = encoder.encodeBuffer(cert.getEncoded());
            System.out.println("hhhhhhhhhhhh-"+psB64Certificate+"-kkkkkkkkkkkkk");

            /* input the signature bytes */
            FileInputStream sigfis = new FileInputStream(externalSign);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );

            sigfis.close();

            /* create a Signature object and initialize it with the public key */
            Signature sig = Signature.getInstance(algorithm, provider);
            sig.initVerify(pubKey);

            /* Update and verify the data */

            FileInputStream datafis = new FileInputStream(file);
            BufferedInputStream bufin = new BufferedInputStream(datafis);

            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                sig.update(buffer, 0, len);
                };

            bufin.close();


            valido = sig.verify(sigToVerify);

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
            Logger.getLogger(FileSignVerifier.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return valido;
    }
    
    public boolean verify(File file) {
        ZipTools tools = new ZipTools();
        ArrayDeque<File> files =tools.uncompressFiles(file.getAbsolutePath());
        File externalSign = null, cert = null, fileToVerify = null;
        for(File f : files){
            String ext = f.getName();
            ext = ext.substring(ext.lastIndexOf("."));
            if(ext.equals(".sig")){
                externalSign = f;
            }
            else if(ext.equals(".cer")){
                cert = f;
        
            }
            else{
                fileToVerify = f;
            }
        }
        return verify(fileToVerify, cert, externalSign);
    }
    
}
