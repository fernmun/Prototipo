package edu.api;

/**
 *
 * This interface represents an abstraction of a signature objects factory method.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public interface SignerCreator {
    /**
     *
     * Returns a concrete signer depending of kind of file.
     * 
     * @param s
     *        {@link String} Creation argument to choice better creator
     * 
     * @return {@link SignerInterface}
     */
    public SignerInterface getSigner(String s);
}
