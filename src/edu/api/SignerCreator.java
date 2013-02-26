/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.api;

/**
 *
 * @author david
 */
public interface SignerCreator {
    /**
     *
     * @param s
     * @return
     */
    public SignerInterface getSigner(String s);
}
