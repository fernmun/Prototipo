package edu.logic.pki;

import edu.logic.tools.ZipTools;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.Certificate;
import java.util.ArrayDeque;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FileSigner implements edu.api.Signer{
    
    private final String algorithm = "SHA1withRSA";
    private final String provider = "SunRsaSign";
    
    /**
     * 
     * @param file
     * @param keyToSign 
     * @return 
     */
    public ArrayDeque<String> sign(File file, PrivateKey keyToSign){
        
        String filePath = file.getParentFile().getPath();
        
        return sign(file, keyToSign, filePath);
    }
    
    /**
     * 
     * @param file
     * @param keyToSign 
     * @param filePath
     * @return 
     */
    public ArrayDeque<String> sign(File file, PrivateKey keyToSign, String filePath){
        ArrayDeque<String> fileNames = new ArrayDeque<String>();
        try{

            /* Generate a key pair */

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();
            System.out.println(pub.getAlgorithm());
            System.out.println(pub.getFormat());
            


            /* Create a Signature object and initialize it with the private key */

            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN"); 

            dsa.initSign(priv);

            /* Update and sign the data */
            fileNames.add(file.getPath());
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bufin = new BufferedInputStream(fis);
            byte[] buffer = new byte[1024];
            int len;
            while (bufin.available() != 0) {
                len = bufin.read(buffer);
                dsa.update(buffer, 0, len);
                };

            bufin.close();

            /* Now that all the data to be signed has been read in, 
                    generate a signature for it */

            byte[] realSig = dsa.sign();
            
//            String path = file.getParentFile().getPath();
            
            String name = file.getName();
            name = name.substring(0, name.lastIndexOf(".")+1);
        
            /* Save the signature in a file */
            String signFile=filePath+"/"+name+"sig";
            fileNames.add(signFile);
            FileOutputStream sigfos = new FileOutputStream(signFile);
            sigfos.write(realSig);

            sigfos.close();

            /* Save the public key in a file */
            byte[] key = pub.getEncoded();
            String pkFile=filePath+"/"+name+"pk";
            fileNames.add(pkFile);
            FileOutputStream keyfos = new FileOutputStream(pkFile);
            keyfos.write(key);

            keyfos.close();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
        
        return fileNames;
    }

    /**
     *
     * @param fileToSign
     * @param outputFile
     * @param keyToSign
     * @param chain
     * @return
     */
    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate[] chain) {
        
        
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
                };

            bufin.close();

            /* Now that all the data to be signed has been read in, 
                    generate a signature for it */

            byte[] realSig = dsa.sign();
            
//            String path = file.getParentFile().getPath();
            
            String name = fileToSign.getName();
            name = name.substring(0, name.lastIndexOf("."));
            String filePath = outputFile.getParentFile().getPath();
            
            /* Save the signature in a file */
            String signFile=filePath+"/"+name+".sig";
            fileNames.add(signFile);
            FileOutputStream sigfos = new FileOutputStream(signFile);
            sigfos.write(realSig);

            sigfos.close();


            /* Save the public key in a file */
            if(chain[0] != null){
                Certificate certificate = chain[0];
                byte[] key = certificate.getEncoded();
                String pkFile=filePath+"/"+name+".cer";
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

    /**
     *
     * @param fileToSign
     * @param keyToSign
     * @param chain
     * @return
     */
    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate[] chain) {
        String filePath = fileToSign.getParentFile().getPath();
        String name = fileToSign.getAbsolutePath();
        name = name.substring(0, name.lastIndexOf("."));
        
            
        return sign(fileToSign, new File(name+"_signed.zip"), keyToSign, chain);
    }
}
