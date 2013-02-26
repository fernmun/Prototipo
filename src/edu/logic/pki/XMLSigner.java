/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.SignerInterface;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;

/**
 *
 * @author david
 */
public class XMLSigner implements SignerInterface{

    /**
     *
     * @param fileToSign
     * @param outputFile
     * @param keyToSign
     * @param chain
     * @return
     */
    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate chain) {
        try{
            // Create a DOM XMLSignatureFactory that will be used to
            // generate the enveloped signature.
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            // Create a Reference to the enveloped document (in this case,
            // you are signing the whole document, so a URI of "" signifies
            // that, and also specify the SHA1 digest algorithm and
            // the ENVELOPED Transform.

            Reference ref = fac.newReference("", fac.newDigestMethod(DigestMethod.SHA1, null),
              Collections.singletonList(fac.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null)), null, null);

            // Create the SignedInfo.
            SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null), Collections.singletonList(ref));

        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;    
    }

    /**
     *
     * @param fileToSign
     * @param keyToSign
     * @param chain
     * @return
     */
    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate certificate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
