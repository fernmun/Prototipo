/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api;

import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 *
 * @author david
 */
public interface Signer {
    /**
     *
     * @param fileToSign
     * @param outputFile
     * @param keyToSign
     * @param chain
     * @return
     */
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate[] chain);
    /**
     *
     * @param fileToSign
     * @param keyToSign
     * @param chain
     * @return
     */
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate[] chain);
}
