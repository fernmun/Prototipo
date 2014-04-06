/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.view;

import edu.api.gui.CommandInterface;
import edu.logic.WebServicesProvider;
import edu.logic.gui.Mediator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;

/**
 *
 * @author DavidCamilo
 */ 
public class UserComboBox extends JComboBox implements CommandInterface{

    public static final int USERS = 1, PROCESSES = 2;
    
    private Mediator mediator;
    private HashMap<Integer, Integer> ids;
    private UserChooseFrame ucf;
    private int type;
            
    public UserComboBox(UserChooseFrame frame, Mediator m, int type) {
        
        ucf = frame;
        this.type = type;
        
        WebServicesProvider provider = WebServicesProvider.getWebServicesProvider();
        HashMap<Integer, String> users;
        if(type == PROCESSES){
            users = provider.getProcesses();
        } else {
             users = provider.getUsers();
        }
        ids = new HashMap<Integer, Integer>();
        int i = 0;
        for (Map.Entry<Integer, String> entry : users.entrySet()) {
            Integer integer = entry.getKey();
            String string = entry.getValue();
            ids.put(i, integer);
            this.addItem(string);
            i++;
        }
        mediator = m;
    }

    public int getType() {
        return type;
    }

    
    @Override
    public void processEvent() {
        if(type == PROCESSES){
            mediator.setProcessToSend(ids.get(new Integer(this.getSelectedIndex())));
            mediator.setUserToSend(-1);
        } else {
            mediator.setUserToSend(ids.get(new Integer(this.getSelectedIndex())));
            mediator.setProcessToSend(-1);
        }
        ucf.dispose();
    }
    
}
