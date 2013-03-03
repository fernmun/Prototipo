/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.SignerInterface;
import edu.api.SignerCreator;

/**
 *
 * @author david
 */
public class ExtFileSignerCreator implements SignerCreator{

    /**
     *
     * @param fileExt
     * @return
     */
    @Override
    public SignerInterface getSigner(String fileExt) {
        
        SignerInterface signer;
        
        if(fileExt.equals("zip")){
            signer = new FileSigner();
        }
        else if(fileExt.equals("pdf")){
            signer = new PDFSigner();
        }
        else if(fileExt.equals("xml") || fileExt.equals("odt")){
            signer = new XMLSigner();
        }
        else{
            signer = new FileSigner();
        }
        
        return signer;
    }
    
}
