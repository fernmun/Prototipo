package edu.logic.pki;

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
 * <code>KeyTools</code> Class allow generate pair of keys, self signed certificates 
 * and CSR (Certificate Signing Request) to handle certificates.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class KeyTools {
    
    protected String algorithmKey = "RSA", 
                     algorithmCert = "SHA1withRSA", 
                     provider = "SunRsaSign";
    protected int keySize = 2 * 1024;
    protected long validityCert = 365 * 86400000l;
    /**
     *
     * Create couple of keys: Public Key and Private Key
     * 
     * @return KeyPair
     */
    public KeyPair generateKeyPair (){
    
        KeyPair pair = null;
        KeyPairGenerator keyGen;
        try {
            keyGen = KeyPairGenerator.getInstance(algorithmKey, provider);
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(keySize, random);
            pair = keyGen.generateKeyPair();
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(KeyTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pair;
    }
    
    /**
     *
     * Generate X509 self signed certificate
     * 
     * @param pair 
     *        {@link KeyPair} Couple of keys
     * @param DN 
     *        {@link String}  Distinguished Name
     * @return {@link Certificate} Self Signed Certificate
     */
    public Certificate generateSelfSignedCertificate(KeyPair pair, String DN){
        
        X509CertImpl cert = null;
        try {            
            PrivateKey privkey = pair.getPrivate();
            X509CertInfo info = new X509CertInfo();
            Date from = new Date();
            Date to = new Date(from.getTime() + validityCert);
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
            cert.sign(privkey, algorithmCert);
            

            // Update the algorith, and resign.
            algo = (AlgorithmId)cert.get(X509CertImpl.SIG_ALG);
            info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
            cert = new X509CertImpl(info);
            cert.sign(privkey, algorithmCert);
            
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
    
    /**
     *
     * Create a file that contains a CSR (Certificate Signing Request) using DN (Distinguished Name) 
     * and couple keys (Public Key and Private Key)
     * 
     * @param DN
     *        {@link String} Distinguished Name
     * @param privateKey
     *        {@link PrivateKey} User private key
     * @param publicKey
     *        {@link PublicKey} User pubic key
     * @param path
     *        {@link String} Path where file is saved
     * @return File 
     *         {@link File} with CSR
     */
    public File generateCSR(String DN, PrivateKey privateKey, PublicKey publicKey, String path){
        
        PKCS10 pkcs10 = new PKCS10(publicKey);
        Signature signature;
        File csr = new File(path);
        try {
            signature = Signature.getInstance(algorithmCert);
            signature.initSign(privateKey);
            X500Name x500Name = new X500Name(DN);
            pkcs10.encodeAndSign(new X500Signer(signature, x500Name));
            //Java 7 patch: pkcs10.encodeAndSign(x500Name, signature);
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
    
    /**
     *
     * Create a file that contains a CSR (Certificate Signing Request) using DN (Distinguished Name) 
     * and couple keys (Public Key and Private Key)
     * 
     * @param DN
     *        {@link String} Distinguished Name
     * @param privateKey
     *        {@link PrivateKey} User private key
     * @param publicKey
     *        {@link PublicKey} User pubic key
     * @return String
     *         {@link String} Certificate Signing Request
     */
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
            pkcs10.encodeAndSign(new X500Signer(signature, x500Name));
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
