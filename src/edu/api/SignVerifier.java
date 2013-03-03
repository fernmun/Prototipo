package edu.api;

import java.io.File;

/**
 *
 * This interface represents an abstraction of needed functionalities to verify 
 * a file signed. It has two verification methods where one of this receives the digital
 * certificate and public signature and another receives a file attached signature
 * and digital certificate.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public interface SignVerifier {
    /**
     *
     * Receive a file signed and then verify it with the public certificate and its 
     * external signature.
     * 
     * @param file
     *        {@link File} <code>File</code> to verify
     * @param publicCert
     *        {@link File} <code>File</code> Public certificate
     * @param externalSign
     *        {@link File} External signature of <code>File</code> signed
     * @return {@link boolean}
     *         Returns true if the signature has validity or false in another way
     */
    public boolean verify(File file, File publicCert, File externalSign);
    /**
     *
     * Receive a file signed and then verify it. The file must attached the signature
     * and the digital certificate.
     * 
     * @param file
     *        {@link File} <code>File</code> to verify
     * @return {@link boolean}
     *         Returns true if the signature has validity or false in another way
     */
    public boolean verify(File file);
}
