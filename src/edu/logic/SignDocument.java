/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.logic;

import edu.api.Signable;
import java.util.Date;

/**
 *
 * @author DavidCamilo
 */
public abstract class SignDocument extends Document implements Signable{

    public SignDocument(int idDocument, String name, String uri, Date created, Date updated) {
        super(idDocument, name, uri, created, updated);
    }

}
