/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.view;

import edu.logic.gui.ButtonHandler;
import edu.logic.gui.Mediator;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 *
 * @author DavidCamilo
 */
public class UserChooseFrame extends JFrame{

    public UserChooseFrame(int width, int height, String title, int posX, int posY, Mediator mediator){
        this.setBounds(posX, posY, width, height);
        this.setTitle(title);
        
        ActionListener listener = new ButtonHandler();
        
//        JPanel pnlContent = new JPanel(new BorderLayout(10, 10));
        JPanel pnlContent = new JPanel(new SpringLayout());
        JComboBox box = new UserComboBox(this, mediator, UserComboBox.USERS);
        box.addActionListener(listener);
        pnlContent.add(new JLabel("Usuario: "));
        pnlContent.add(box);
        box = new UserComboBox(this, mediator, UserComboBox.PROCESSES);
        box.addActionListener(listener);
        pnlContent.add(new JLabel("Convocatoria: "));
        pnlContent.add(box);
        SpringUtilities.makeCompactGrid(pnlContent, 2, 2, 10, 10, 10, 10);
        this.add(pnlContent);
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
