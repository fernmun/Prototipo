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
    public boolean verify(File file, File publicCert, File externalSign);
    public boolean verify(File file);
}
