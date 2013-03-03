/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import java.util.Arrays;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class DocumentListTableModel extends AbstractTableModel {
    
        String[] columnNames;
        Object[][] data ;
        int[] editableCols = {};

//        public final Object[] longValues = {Boolean.FALSE, "Documento 1", "1 Ago/2012"};
        
        /**
     *
     * @param data
     * @param columnNames
     */
    public DocumentListTableModel(Object[][] data, String[] columnNames){
            this.columnNames = columnNames;
            this.data = data;
        }
        
        /**
     *
     * @param data
     * @param columnNames
     * @param editableCols
     */
    public DocumentListTableModel(Object[][] data, String[] columnNames, int[] editableCols){
            this.columnNames = columnNames;
            this.data = data;
            this.editableCols = editableCols;
        }
        
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
            if (Arrays.binarySearch(editableCols, col) < 0) {
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
