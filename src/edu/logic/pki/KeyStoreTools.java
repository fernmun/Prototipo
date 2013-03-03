package edu.logic.pki;

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
 * <code>KeyStoreTools</code> Class manage keystore to storage easily cryptographic
 * keys and digital certificates.
 * <p>
 * It facilitates use the <code>KeyStore</code> Class of Java
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class KeyStoreTools {
    
    private KeyStore ks;
    private String strPath;
    
    /**
     * 
     * This constructor allow to initialize the class to create after
     * many keystores
     */
    public KeyStoreTools(){
        
    }
    
    /**
     *
     * This constructor initialize a keystore. If it exists it tries to load the KeyStore
     * if it doesn't it creates a new KeyStore
     * 
     * @param path
     *        {@link String} Path to save the KeyStore
     * @param password
     *        {@link String} Password to access the KeyStore
     */
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
     * Create a new KeyStore setting a path to save it and a password to access it.
     * 
     * @param path
     *        {@link String} Path to save the KeyStore
     * @param password
     *        {@link String} Password to access the KeyStore
     * @return {@link boolean}
     *          True if it can create the KeyStore or False in another way
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
    
    /**
     *
     * Load an existing KeyStore setting a path to save it and a 
     * password to access it.
     * 
     * @param path
     *        {@link String} Path to save the KeyStore
     * @param password
     *        {@link String} Password to access the KeyStore
     * @return {@link boolean}
     *          True if it can create the KeyStore or False in another way
     */
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

        return bSuccess;
    }
    
    /**
     *
     * Add certificate to the last KeyStore loaded.
     * 
     * @param alias
     *        {@link String} Unique alias to identify a certificate
     * @param certificate
     *        {@link Certificate} Certificate to save
     * @param password
     *        {@link String} Password to access the KeyStore element
     * @return {@link boolean}
     *         True if it can add the certificate to the KeyStore or False in another way
     */
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
    
    /**
     *
     * Allow to get a certificate from the current KeyStore.
     * 
     * @param alias
     *        {@link String} Unique alias to identify a certificate
     *          
     * @return {@link Certificate} 
     *         Certificate obtained of current KeyStore
     */
    public Certificate getCertificate(String alias){
        Certificate certificate = null;
        try {
            certificate = ks.getCertificate(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return certificate;
    }
    
    /**
     *
     * Add a private key to the current KeyStore and a certificate associated to this.
     * 
     * @param alias
     *        {@link String} Unique alias to identify the private key
     * @param key
     *        {@link PrivateKey} The private key to store
     * @param password
     *        {@link String} Password to access the KeyStore element
     * @param chain
     *        {@link Certificate} Array of one or more certificates
     * @return {@link boolean}
     *         True if it can add the certificate to the KeyStore or False in another way
     */
    public boolean addPrivateKey(String alias, PrivateKey key, String password, Certificate[] chain){
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
    
   /**
     *
     * Allow to get a key from the current KeyStore.
     * 
     * @param alias
     *        {@link String} Unique alias to identify a key
     * @param password
     *        {@link String} Password to access the KeyStore element        
     * @return {@link Key} 
     *         Key obtained of current KeyStore
     */
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
    
    /**
     *
     * Allow to get a certificate chain from the current KeyStore.
     * 
     * @param alias
     *        {@link String} Unique alias to identify a certificate
     *          
     * @return {@link Certificat[]} 
     *         Chain of certificates obtained of current KeyStore
     */
    public Certificate[] getCertificateChain(String alias){
        Certificate[] chain = new Certificate[]{};
        try {
            chain = ks.getCertificateChain(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chain;
    }
    
    /**
     *
     * List all certificate aliases and key aliases saved on current KeyStore.
     * 
     * @return {@link String}
     *         Array of aliases
     */
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
    
    /**
     *
     * List all key aliases saved on current KeyStore.
     * 
     * @return {@link String}
     *         Array of aliases
     */
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
    
    /**
     *
     * List all certificate aliases saved on current KeyStore.
     * 
     * @return {@link String}
     *         Array of aliases
     */
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
    
    /**
     *
     * Get creation date of an alias saved on KeyStore.
     * 
     * @return {@link Date}
     *         Creation date of alias. Return null if alias doesn't exist
     */
    public Date getAliasDate(String alias){
        Date created = null;
        try {
            created = ks.getCreationDate(alias);
        } catch (KeyStoreException ex) {
            Logger.getLogger(KeyStoreTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return created;
    }
    
    /**
     *
     * Return current KeyStore.
     * 
     * @return {@link KeyStore}
     *         Current KeyStore
     */
    public KeyStore getKeyStore(){
        return ks;
    }
    
    /**
     *
     * @param cert
     * @throws Exception
     */
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
        
//        addPrivateKey("certificado", (PrivateKey) key, "password", new Certificate[]{cert});
//        ks.setCertificateEntry("testkey", cert);
//        System.out.println(chain[0].getPublicKey());
//        System.out.println(keyTools.generateCSR("CN=Test, L=London, ST=Stale ,C=GB, O=Test", (PrivateKey)key, publicKey,"test.csr"));
        
    }
    
}
