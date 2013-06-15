package edu.logic.pki;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import edu.api.SignerInterface;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        File signedFile = null;
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            PrivateKey pk = keyToSign;
            Certificate[] chain = {certificate};
            // Creating the reader and the stamper
            PdfReader reader = new PdfReader(fileToSign.getAbsolutePath());
            FileOutputStream os = new FileOutputStream(outputFile.getAbsoluteFile());
            PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0', null, true);
            // Creating the appearance
            PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
            appearance.setVisibleSignature(new Rectangle(0, 0, 0, 0), 1, "signature");
            appearance.setCertificationLevel(PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
            // Creating the signature
            ExternalSignature pks = new PrivateKeySignature(pk, "SHA-256", "BC");
            ExternalDigest digest = new BouncyCastleDigest();
            MakeSignature.signDetached(appearance, digest, pks, chain, null, null, null, 0, MakeSignature.CryptoStandard.CMS);
            signedFile = outputFile;
        } catch (IOException ex) {
            Logger.getLogger(PDFSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(PDFSigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signedFile;
    }
    
    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate certificate) {
        
        String name = fileToSign.getAbsolutePath();
        String ext =  name.substring(name.lastIndexOf(".")+1);
        name = name.substring(0, name.lastIndexOf("."));
        
        return sign(fileToSign, new File(name+"_signed."+ext), keyToSign, certificate);
    }
    
}
