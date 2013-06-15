/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.security.PdfPKCS7;
import edu.api.SignVerifier;
import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author david
 */
public class PDFSignVerifier  implements SignVerifier{

    @Override
    public boolean verify(File file, File publicCert, File externalSign) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean verify(File file) {
        boolean isValid = false;
        
        try {
            BouncyCastleProvider provider = new BouncyCastleProvider();
            Security.addProvider(provider);
            
            PdfReader reader = new PdfReader(file.getAbsolutePath());
            
            AcroFields fields = reader.getAcroFields();
            ArrayList<String> names = fields.getSignatureNames();
            PdfPKCS7 pkcs7 = fields.verifySignature("signature");
            isValid =  pkcs7.verify();
        } catch (SignatureException ex) {
            Logger.getLogger(PDFSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PDFSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isValid;
    }
    
}
