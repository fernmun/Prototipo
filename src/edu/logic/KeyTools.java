/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.pkcs.PKCS10;
import sun.security.x509.*;


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
            keyGen = KeyPairGenerator.getInstance("RSA", "SunRsaSign");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(4*1024, random);
            pair = keyGen.generateKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pair;
    }
    
    public Certificate generateSelfSignedCertificate(KeyPair pair, String DN){
        String algorithm = "SHA1withRSA";
        X509CertImpl cert = null;
        try {
            //http://bfo.com/blog/2011/03/08/odds_and_ends_creating_a_new_x_509_certificate.html
            //http://blog.thilinamb.com/2010/01/how-to-generate-self-signed.html
            PrivateKey privkey = pair.getPrivate();
            X509CertInfo info = new X509CertInfo();
            Date from = new Date();
            Date to = new Date(from.getTime() + 365 * 86400000l);
            CertificateValidity interval = new CertificateValidity(from, to);
            BigInteger sn = new BigInteger(64, new SecureRandom());
            X500Name owner = new X500Name(DN);

            info.set(X509CertInfo.VALIDITY, interval);
            info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
            info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(owner));
            info.set(X509CertInfo.ISSUER, new CertificateIssuerName(owner));
            info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
            info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
            AlgorithmId algo = new AlgorithmId(AlgorithmId.sha1WithRSAEncryption_oid);
            info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));

            // Sign the cert to identify the algorithm that's used.
            cert = new X509CertImpl(info);
            cert.sign(privkey, algorithm);
            

            // Update the algorith, and resign.
            algo = (AlgorithmId)cert.get(X509CertImpl.SIG_ALG);
            info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
            cert = new X509CertImpl(info);
            cert.sign(privkey, algorithm);
            
        } catch (IOException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cert;
    }
    
   public File generateCSR(String DN, PrivateKey privateKey, PublicKey publicKey, String path){
        // generate PKCS10 certificate request
        String sigAlg = "SHA1withRSA";
        PKCS10 pkcs10 = new PKCS10(publicKey);
        Signature signature;
        File csr = new File(path);
        try {
            signature = Signature.getInstance(sigAlg);
            signature.initSign(privateKey);
            // common, orgUnit, org, locality, state, country
            X500Name x500Name = new X500Name(DN);
            pkcs10.encodeAndSign(x500Name, signature);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(csr);
            pkcs10.print(ps);
        } catch (CertificateException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return csr;
    }
    
    public String generateCSR(String DN, PrivateKey privateKey, PublicKey publicKey){
        // generate PKCS10 certificate request
        String sigAlg = "SHA1withRSA";
        PKCS10 pkcs10 = new PKCS10(publicKey);
        Signature signature;
        String csr = "";
        try {
            signature = Signature.getInstance(sigAlg);
            signature.initSign(privateKey);
            // common, orgUnit, org, locality, state, country
            X500Name x500Name = new X500Name(DN);
            pkcs10.encodeAndSign(x500Name, signature);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(bs);
            pkcs10.print(ps);
            csr = bs.toString();
        } catch (CertificateException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return csr;
    }
    
}
