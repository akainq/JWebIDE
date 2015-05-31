/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.autocomplite;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.TextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

/**
 *
 * @author kuznetsov
 */
public class AutocompliteContextFrame extends JWindow {
    
    JTextComponent textComp;
    JScrollPane jScrollPane1;
    JTable  jTable1;
    
    public AutocompliteContextFrame(JFrame owner) {
        
        
           super(owner);
           windowInit();                                
           this.setFocusable(false);
           this.setFocusableWindowState(false);
           
            jScrollPane1 = new javax.swing.JScrollPane();           
            jTable1 = new javax.swing.JTable();            
            jScrollPane1.setFocusable(true);
            
       // setAutoRequestFocus(true);       
     //   setType(java.awt.Window.Type.POPUP);
       jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null}
            },
            new String [] {
                ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
       
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setFillsViewportHeight(true);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRequestFocusEnabled(true);
        jTable1.setRowHeight(20);
        jTable1.setShowVerticalLines(false);
        jTable1.getTableHeader().setResizingAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        //jTable1.setVerifyInputWhenFocusTarget(false);
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }     
            
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }
    
    public void SetListData(String [] list) {
     
        jTable1.setModel(new CompliteTableModel(list));
     
    }
    
    private void jTable1KeyReleased(KeyEvent evt) {
            
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            this.setFocusableWindowState(false);
            this.setFocusable(false);
            setText();
            evt.consume();
            this.setVisible(false);
           
           
        }
     }
    
    void GetFocusList(JTextComponent textComp) {
        this.textComp = textComp;
        try {
            Robot robot = new Robot();
            this.setVisible(false);
            this.setFocusable(true);
            this.requestFocusInWindow();
            this.requestFocus();
            this.setVisible(true);
            jTable1.setFocusable(true);
            jTable1.requestFocus();
            //jTable1.setRowSelectionInterval(0, 0);
            this.jTable1.requestFocusInWindow();
            robot.keyPress(KeyEvent.VK_DOWN);
            
        } catch (AWTException ex) {
            Logger.getLogger(AutocompliteContextFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    
    void setText(){
        
        try {
            int rowNum = jTable1.getSelectedRow();
            
            if(rowNum == -1) return;
            String val = (String) this.jTable1.getModel().getValueAt(rowNum, 0);
            int pos = textComp.getCaretPosition();
            int wordStart = Utilities.getPreviousWord(textComp,pos );
            String key = textComp.getText().substring(wordStart,textComp.getCaretPosition());
            if(!"".equals(val) && val!=null){
                if(key.equals(".")) wordStart++;
                StringBuilder myName = new StringBuilder(textComp.getText());
                myName.replace(wordStart, pos, val);
                textComp.setText(myName.toString());
                textComp.setCaretPosition(wordStart+val.length());
            }
        } catch (BadLocationException ex) {
            Logger.getLogger(AutocompliteContextFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         
    }
    
}
