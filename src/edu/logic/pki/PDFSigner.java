/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.Signer;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class PDFSigner implements Signer{

    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate[] chain) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate[] chain) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
