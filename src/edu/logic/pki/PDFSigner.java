/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.Signer;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 *
 * @author lmparra
 */
public class PDFSigner implements Signer{

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
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
