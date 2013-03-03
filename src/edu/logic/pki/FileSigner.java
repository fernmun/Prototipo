package edu.logic.pki;

import edu.api.SignerInterface;
import edu.logic.tools.ZipTools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.ArrayDeque;

/**
 *
 * <code>FileSigner</code> class is a concrete signer to sign whatever kind of file. 
 * It implements all needed methods of <code>{@link SignerInterface}</code> interface.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FileSigner implements SignerInterface{
    
    private final String algorithm = "SHA1withRSA";
    private final String provider = "SunRsaSign";
   
    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate certificate) {
        
        try{
            ArrayDeque<String> fileNames = new ArrayDeque<String>();

            /* Create a Signature object and initialize it with the private key */

            Signature dsa = Signature.getInstance(algorithm, provider); 

            dsa.initSign(keyToSign);

            /* Update and sign the data */
            fileNames.add(fileToSign.getAbsolutePath());
            FileInputStream fis = new FileInputStream(fileToSign);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
            }

            bufin.close();

            /* Now that all the data to be signed has been read in, 
                    generate a signature for it */

            byte[] realSig = dsa.sign();          
            
            String name = fileToSign.getName();
            name = name.substring(0, name.lastIndexOf("."));
            String filePath = outputFile.getParentFile().getPath();
            
            /* Save the signature in a file */
            String signFile=filePath + "/" + name + ".sig";
            fileNames.add(signFile);
            FileOutputStream sigfos = new FileOutputStream(signFile);
            sigfos.write(realSig);

            sigfos.close();


            /* Save the public key in a file */
            if(certificate != null){
                byte[] key = certificate.getEncoded();
                String pkFile=filePath + "/" + name + ".cer";
                fileNames.add(pkFile);
                FileOutputStream keyfos = new FileOutputStream(pkFile);
                keyfos.write(key);
                keyfos.close();
            }
            
            ZipTools zipTools = new ZipTools();
            zipTools.compressFiles(fileNames, outputFile.getAbsolutePath());
            if(outputFile.exists())
                return outputFile;
            
        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        
        return null;
    }

    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate certificate) {
        String filePath = fileToSign.getParentFile().getPath();
        String name = fileToSign.getAbsolutePath();
        name = name.substring(0, name.lastIndexOf("."));
        
            
        return sign(fileToSign, new File(name + "_signed.zip"), keyToSign, certificate);
    }
}
