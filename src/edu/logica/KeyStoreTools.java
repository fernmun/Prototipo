/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dnova
 */
public class KeyStoreTools {
    
    private KeyStore ks;
    private String strPath;
    
    public KeyStoreTools(){
        
    }
    public KeyStoreTools(String path, String password){
        createKeyStore(path, password);
//        loadKeyStore(path, password);
    }
   
    /**
     * 
     * @param path
     * @param password
     * @return 
     */
    public boolean createKeyStore(String path, String password){
        boolean bSuccess = false;
        if(ks == null){
            try {
                ks = KeyStore.getInstance(KeyStore.getDefaultType());
                
                ks.load(null, null);
                
                FileOutputStream fosKeyStore = new FileOutputStream(path);
                ks.store(fosKeyStore, password.toCharArray());
                fosKeyStore.close();
                
                strPath = path;
                bSuccess = true;
                
            } catch (KeyStoreException ex) {
                Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bSuccess;
    }
    
    public boolean loadKeyStore(String path, String password){
        boolean bSuccess = false;
        
        try {
            
            FileInputStream fisKeyStore = new FileInputStream(path);
            ks.load(fisKeyStore, password.toCharArray());
            fisKeyStore.close();
            
            bSuccess = true;
            
        } catch (IOException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
      

        bSuccess = true;
        return bSuccess;
    }
    
    public boolean addCertificate(String alias, Certificate certificate, String password){
        boolean bSuccess = false;
        try {
            ks.setCertificateEntry(alias, certificate);
            
            FileOutputStream fosKeyStore = new FileOutputStream(strPath);
            ks.store(fosKeyStore, password.toCharArray());
            fosKeyStore.close();
            bSuccess = true;
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bSuccess;
    }
    
    public Certificate getCertificate(String alias){
        Certificate certificate = null;
        try {
            certificate = ks.getCertificate(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return certificate;
    }
    
}
