/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api;

import java.io.File;
import java.security.cert.Certificate;

/**
 *
 * @author david
 */
public interface SignVerifier {
    /**
     *
     * @param file
     * @param publicCert
     * @param externalSign
     * @return
     */
    public boolean verify(File file, File publicCert, File externalSign);
    /**
     *
     * @param file
     * @return
     */
    public boolean verify(File file);
}
