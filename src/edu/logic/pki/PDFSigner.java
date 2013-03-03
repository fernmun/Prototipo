package edu.logic.pki;

import edu.api.SignerInterface;
import java.io.File;
import java.security.PrivateKey;
import java.security.cert.Certificate;

/**
 *
 * <code>PDFSigner</code> class is a concrete signer to sign PDF files. It implements
 * all needed methods of <code>{@link SignerInterface}</code> interface.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class PDFSigner implements SignerInterface{

    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate certificate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate certificate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
