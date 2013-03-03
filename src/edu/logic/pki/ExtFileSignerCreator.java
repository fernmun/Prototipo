/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.SignerCreator;
import edu.api.SignerInterface;

/**
 *
 * <code>ExtFileSignerCreator</code> class is a concrete creator that decides
 * which instance of signer class is better to sign a file by its extension.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class ExtFileSignerCreator implements SignerCreator{

    @Override
    public SignerInterface getSigner(String fileExt) {
        
        SignerInterface signer;
        
        if(fileExt.equals("zip")){
            signer = new FileSigner();
        }
        else if(fileExt.equals("pdf")){
            signer = new PDFSigner();
        }
        else if(fileExt.equals("xml")){
            signer = new XMLSigner();
        }
        else{
            signer = new FileSigner();
        }
        
        return signer;
    }
    
}
