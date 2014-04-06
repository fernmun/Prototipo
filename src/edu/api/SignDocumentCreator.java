/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.api;

import edu.logic.SignDocument;

/**
 *
 * @author DavidCamilo
 */
public interface SignDocumentCreator {
    public SignDocument getSignDocument(String s);
}
