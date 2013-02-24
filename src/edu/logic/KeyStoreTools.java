/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Enumeration;
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
        File fileKeyStore = new File(path);
        if(fileKeyStore.exists()){
            loadKeyStore(path, password);
        }
        else{
            createKeyStore(path, password);
        }
        
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
            if(ks == null){
                ks = KeyStore.getInstance(KeyStore.getDefaultType());
                strPath = path;
            }
            
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
        } catch (KeyStoreException ex) {
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
    
    public boolean addKey(String alias, PrivateKey key, String password, Certificate[] chain){
        boolean bSuccess = false;
        try {
            
            ks.setKeyEntry(alias, key, password.toCharArray(), chain);
            
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
    
    public Key getKey(String alias, char[] password){
        Key pkey = null;
        try {
            pkey = ks.getKey(alias, password);
            
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableKeyException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pkey;
    }
    public Certificate[] getCertificateChain(String alias){
        Certificate[] chain = new Certificate[]{};
        try {
            chain = ks.getCertificateChain(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chain;
    }
    public String[] getAliasList(){
        
        ArrayDeque<String> alias = new ArrayDeque<String>();
        
        Enumeration en;
        try {
            en = ks.aliases();
            while (en.hasMoreElements()) {
                Object object = en.nextElement();
                alias.add(object.toString());
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alias.toArray(new String[0]);
    }
    public String[] getKeyList(){
        
        ArrayDeque<String> alias = new ArrayDeque<String>();
        
        Enumeration en;
        try {
            en = ks.aliases();
            while (en.hasMoreElements()) {
                Object object = en.nextElement();
                if(ks.isKeyEntry(object.toString()))
                    alias.add(object.toString());
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alias.toArray(new String[0]);
    }
    public String[] getCertificateList(){
        
        ArrayDeque<String> alias = new ArrayDeque<String>();
        
        Enumeration en;
        try {
            en = ks.aliases();
            while (en.hasMoreElements()) {
                Object object = en.nextElement();
                if(ks.isCertificateEntry(object.toString()))
                    alias.add(object.toString());
            }
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alias.toArray(new String[0]);
    }
    
    public Date getAliasDate(String alias){
        Date created = null;
        try {
            created = ks.getCreationDate(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return created;
    }
    
    public KeyStore getKeyStore(){
        return ks;
    }
    
    public void testKeyStore(Certificate cert) throws Exception{
        
        Key key = ks.getKey("testkey", "password".toCharArray());
        Certificate[] chain = ks.getCertificateChain("testkey");
        PublicKey publicKey = chain[0].getPublicKey();
        System.out.println(chain[0]);
//        System.out.println(publicKey);
//        System.out.println(key);
        
        Enumeration en = ks.aliases();
        while (en.hasMoreElements()) {
            Object object = en.nextElement();
            System.out.println(object);
        }
        Key pkey = ks.getKey("certificado", "password".toCharArray());
        chain = ks.getCertificateChain("certificado");
        publicKey = chain[0].getPublicKey();
        System.out.println(chain[0]);
//        System.out.println(publicKey);
//        System.out.println(pkey);
        
//        addKey("certificado", (PrivateKey) key, "password", new Certificate[]{cert});
//        ks.setCertificateEntry("testkey", cert);
//        System.out.println(chain[0].getPublicKey());
//        System.out.println(keyTools.generateCSR("CN=Test, L=London, ST=Stale ,C=GB, O=Test", (PrivateKey)key, publicKey,"test.csr"));
        
    }
    
}
