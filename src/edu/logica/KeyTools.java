/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logica;

import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnova
 */
public class KeyTools {
    
    public KeyPair generateKeyPair (){
    
        /* Generate a key pair */
        KeyPair pair = null;
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(1024, random);
            pair = keyGen.generateKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pair;
    }
    
}
