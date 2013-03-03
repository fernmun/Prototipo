package edu.api;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 *
 * This interface represents an abstraction of needed methods required to sign
 * whatever kind of file through signer concrete class.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public interface SignerInterface {
    /**
     *
     * Sign a <code>File</code> object that receives the private key and certificate. It
     * generates another <code>File</code> object digitally signed.
     * 
     * @param fileToSign
     *        {@link File} <code>File</code> object to sign with private key and certificate
     * @param outputFile
     *        {@link File} Resulting <code>File</code> object after being signed
     * @param keyToSign
     *        {@link PrivateKey} Private Key used to sign the file object
     * @param certificate
     *        {@link Certificate} Certificate used to identify who sign
     * @return {@link File}
     *         File signed or null if there is no output
     */
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate certificate);
   
    /**
     *
     * Sign a <code>File</code> object that receives the private key and certificate. It
     * generates another <code>File</code> object digitally signed.
     * <p>
     * The name of output file is generated automatically. Resulting name: <i>File Name</i>_signed
     * 
     * @param fileToSign
     *        {@link File} <code>File</code> object to sign with private key and certificate
     * @param keyToSign
     *        {@link PrivateKey} Private Key used to sign the file object
     * @param certificate
     *        {@link Certificate} Certificate used to identify who sign
     * @return {@link File}
     *         File signed or null if there is no output
     */
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate chain);
}
