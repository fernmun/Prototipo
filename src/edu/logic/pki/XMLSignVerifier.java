/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.SignVerifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyException;
import java.security.PublicKey;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author david
 */
public class XMLSignVerifier implements SignVerifier{

    @Override
    public boolean verify(File file, File publicCert, File externalSign) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean verify(File file) {
        boolean isValid = false;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc =
                dbf.newDocumentBuilder().parse(new FileInputStream(file));

            NodeList nl = 
                doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
            if (nl.getLength() == 0) {
                try {
                    throw new Exception("Cannot find Signature element");
                } catch (Exception ex) {
                    Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
            
            boolean coreValidity = true;
            
            for(int n = nl.getLength()-1; n >= 0; n--){
                DOMValidateContext valContext = new DOMValidateContext
                    (new KeyValueKeySelector(), nl.item(n));

                XMLSignature signature = fac.unmarshalXMLSignature(valContext);

                coreValidity &= signature.validate(valContext); 
                
                if(!coreValidity){
                    break;
                }
                else{
                    nl.item(n).getParentNode().removeChild(nl.item(n));
                }
            }
            
            
            if (coreValidity == true) {
                isValid = true;
            }
        } catch (XMLSignatureException ex) {
            Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MarshalException ex) {
            Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLSignVerifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return isValid;
    }
    
    /**
     * KeySelector which retrieves the public key out of the
     * KeyValue element and returns it.
     * NOTE: If the key algorithm doesn't match signature algorithm,
     * then the public key will be ignored.
     */
    private static class KeyValueKeySelector extends KeySelector {
        @Override
	public KeySelectorResult select(KeyInfo keyInfo,
                                        KeySelector.Purpose purpose,
                                        AlgorithmMethod method,
                                        XMLCryptoContext context)
            throws KeySelectorException {
            if (keyInfo == null) {
		throw new KeySelectorException("Null KeyInfo object!");
            }
            SignatureMethod sm = (SignatureMethod) method;
            List list = keyInfo.getContent();

            for (int i = 0; i < list.size(); i++) {
		XMLStructure xmlStructure = (XMLStructure) list.get(i);
            	if (xmlStructure instanceof KeyValue) {
                    PublicKey pk = null;
                    try {
                        pk = ((KeyValue)xmlStructure).getPublicKey();
                    } catch (KeyException ke) {
                        throw new KeySelectorException(ke);
                    }
                    // make sure algorithm is compatible with method
                    if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
                        return new SimpleKeySelectorResult(pk);
                    }
		}
            }
            throw new KeySelectorException("No KeyValue element found!");
	}

        //@@@FIXME: this should also work for key types other than DSA/RSA
	static boolean algEquals(String algURI, String algName) {
            if (algName.equalsIgnoreCase("DSA") &&
		algURI.equalsIgnoreCase(SignatureMethod.DSA_SHA1)) {
		return true;
            } else if (algName.equalsIgnoreCase("RSA") &&
                       algURI.equalsIgnoreCase(SignatureMethod.RSA_SHA1)) {
		return true;
            } else {
		return false;
            }
	}
    }
    
    private static class SimpleKeySelectorResult implements KeySelectorResult {
	private PublicKey pk;
	SimpleKeySelectorResult(PublicKey pk) {
	    this.pk = pk;
	}

	public Key getKey() { return pk; }
    }
    
}
