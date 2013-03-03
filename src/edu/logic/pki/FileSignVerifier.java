package edu.logic.pki;

import edu.api.SignVerifier;
import edu.logic.tools.ZipTools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

/**
 *
 * <code>FileSignVerifier</code> class allow to verify a file signed by an instance 
 * of <code>FileSigner</code> Class. It has two verification methods where one of 
 * this receives the digital certificate and public signature and another receives 
 * a file attached signature and digital certificate.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FileSignVerifier implements SignVerifier{

    private final String algorithm = "SHA1withRSA";
    private final String provider = "SunRsaSign";
    private final String certType = "X.509";
    
    @Override
    public boolean verify(File file, File publicCert, File externalSign) {
        boolean valido=false;
        
        try{
            /* Import encoded public key */
            CertificateFactory certFactory = CertificateFactory.getInstance(certType);
            FileInputStream fis = new FileInputStream(publicCert);

            Certificate cert = certFactory.generateCertificate(fis);
            fis.close();
            PublicKey pubKey = cert.getPublicKey();
            /*
            BASE64Encoder encoder=new BASE64Encoder();
            String psB64Certificate = encoder.encodeBuffer(cert.getEncoded());
            */

            /* Input the signature bytes */
            FileInputStream sigfis = new FileInputStream(externalSign);
            byte[] sigToVerify = new byte[sigfis.available()]; 
            sigfis.read(sigToVerify );

            sigfis.close();

            /* Create a Signature object and initialize it with the public key */
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
    
    @Override
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
