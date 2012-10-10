/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.UIBuilder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author DavidCamilo
 */
public class InboxUIBuilder extends UIBuilder{

    JTabbedPane jtpDocuments;
    JLabel lblInbox;
    JTable tblInbox;
    
    @Override
    public void addUIControls() {
        panelUI = new JPanel(new BorderLayout(20, 20));
        
        jtpDocuments = new JTabbedPane();
        jtpDocuments.setTabPlacement(JTabbedPane.TOP);
        
        JPanel pnlInbox = buildInbox();
        JPanel pnlReadbox = buildReadbox();
        
        jtpDocuments.addTab("Recibidos", pnlInbox);
        jtpDocuments.addTab("Leídos", new JPanel());
        
        panelUI.add(new JPanel(), BorderLayout.NORTH);
        panelUI.add(new JPanel(), BorderLayout.WEST);
        panelUI.add(new JPanel(), BorderLayout.EAST);
        panelUI.add(new JPanel(), BorderLayout.SOUTH);
        panelUI.add(jtpDocuments, BorderLayout.CENTER);
    }

    @Override
    public void initialize() {
        System.out.println("Not supported yet.");
    }
    
    private JPanel buildInbox(){
        JPanel pnlInbox = new JPanel(new BorderLayout(10, 10));
        
        lblInbox = new JLabel("Documentos Recibidos:");
        
        String[] columnNames = {" ", "Nombre", "Fecha"};
        Object[][] data = {
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"}
        };
        
        tblInbox = new JTable(new MyTableModel());
        tblInbox.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tblInbox.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tblInbox);
        //tblInbox.se
        
        pnlInbox.add(lblInbox, BorderLayout.NORTH);
        pnlInbox.add(scrollPane, BorderLayout.CENTER);
        
        return pnlInbox;
    }
    
    private JPanel buildReadbox(){
        
        return new JPanel();
    }
    
    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"First Name",
                                        "Last Name",
                                        "Sport",
                                        "# of Years",
                                        "Vegetarian"};
        private Object[][] data = {
	    {"Kathy", "Smith",
	     "Snowboarding", new Integer(5), new Boolean(false)},
	    {"John", "Doe",
	     "Rowing", new Integer(3), new Boolean(true)},
	    {"Sue", "Black",
	     "Knitting", new Integer(2), new Boolean(false)},
	    {"Jane", "White",
	     "Speed reading", new Integer(20), new Boolean(true)},
	    {"Joe", "Brown",
	     "Pool", new Integer(10), new Boolean(false)}
        };

        public final Object[] longValues = {"Jane", "Kathy",
                                            "None of the above",
                                            new Integer(20), Boolean.TRUE};

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {

            data[row][col] = value;
            fireTableCellUpdated(row, col);

        }
    }
    
}
