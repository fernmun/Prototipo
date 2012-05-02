/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logica;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.util.Vector;

/**
 *
 * @author dnova
 */
public class Signer {
    
    /**
     * 
     * @param file
     * @return 
     */
    public Vector<String> sign(File file, PrivateKey keyToSign){
        
        String filePath = file.getParentFile().getPath();
        
        return sign(file, keyToSign, filePath);
    }
    
    /**
     * 
     * @param file
     * @param filePath
     * @return 
     */
    public Vector<String> sign(File file, PrivateKey keyToSign, String filePath){
        Vector<String> fileNames = new Vector<String>();
        try{

            /* Generate a key pair */

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");

            keyGen.initialize(1024, random);

            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey priv = pair.getPrivate();
            PublicKey pub = pair.getPublic();


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
}
