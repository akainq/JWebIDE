/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.autocomplite;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Aleksey
 */
public class CompliteTableModel implements TableModel {

    String [] dataArray;
     
    public CompliteTableModel(String [] data) {
        this.dataArray = data;
    }

    
    
    
    @Override
    public int getRowCount() {
       return dataArray.length;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
           return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
             return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if(rowIndex > dataArray.length) return new Object();
        
        return  dataArray[rowIndex];
        
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        
    }
    
}
