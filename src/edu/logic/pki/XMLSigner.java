package edu.logic.pki;

import edu.api.SignerInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * <code>XMLSigner</code> class is a concrete signer to sign XML files. It implements
 * all needed methods of <code>{@link SignerInterface}</code> interface.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class XMLSigner implements SignerInterface{

    @Override
    public File sign(File fileToSign, File outputFile, PrivateKey keyToSign, Certificate certificate) {
        File out = null;
        try{
            
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            Reference ref = fac.newReference
                ("", fac.newDigestMethod(DigestMethod.SHA1, null),
                 Collections.singletonList
                  (fac.newTransform
                    (Transform.ENVELOPED, (TransformParameterSpec) null)), 
                 null, null);

            SignedInfo si = fac.newSignedInfo
                (fac.newCanonicalizationMethod
                 (CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS, 
                  (C14NMethodParameterSpec) null), 
                 fac.newSignatureMethod(getSignatureMethodString(keyToSign.getAlgorithm()), null),
                 Collections.singletonList(ref));

            KeyInfoFactory kif = fac.getKeyInfoFactory();
            KeyValue kv = kif.newKeyValue(certificate.getPublicKey());
            
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = 
                dbf.newDocumentBuilder().parse(new FileInputStream(fileToSign));

            DOMSignContext dsc = new DOMSignContext
                (keyToSign, doc.getDocumentElement());

            XMLSignature signature = fac.newXMLSignature(si, ki, null,"signature","signature_value");

            signature.sign(dsc);

            OutputStream os = new FileOutputStream(outputFile);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(new DOMSource(doc), new StreamResult(os));
            if(outputFile.length() > 0L) {
                out = outputFile;
            }
            
        } catch (MarshalException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLSignatureException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        }  catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyException ex) {
            Logger.getLogger(XMLSigner.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;    
    }

    @Override
    public File sign(File fileToSign, PrivateKey keyToSign, Certificate certificate) {
        
        String name = fileToSign.getAbsolutePath();
        String ext =  name.substring(name.lastIndexOf(".")+1);
        name = name.substring(0, name.lastIndexOf("."));
        
        return sign(fileToSign, new File(name+"_signed."+ext), keyToSign, certificate);
    }
    /**
     * 
     * Returns the Signature Method given the key algorithm.
     * 
     * @param keyAgorithm
     *        {@link String} Kind of key algorithm
     * @return {@link String} Signature method to use
     */
    private String getSignatureMethodString(String keyAgorithm){
        // Default value
        String method = SignatureMethod.RSA_SHA1;
        
        if(keyAgorithm.equals("RSA")){
            method = SignatureMethod.RSA_SHA1;
        }
        else if(keyAgorithm.equals("DSA")){
            method = SignatureMethod.DSA_SHA1;
        }
        
        return method;
    }
    
}
