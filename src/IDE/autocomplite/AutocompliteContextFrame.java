/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.autocomplite;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
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
    AutocompliteContextFrame me;
    int CurrentRowSelection = 0;
    public AutocompliteContextFrame(JTextComponent textComp) {
        
        
           super();
          me = this;
           windowInit();                                
           this.setFocusable(true);
           this.setFocusableWindowState(true);
            this.textComp = textComp;
            jScrollPane1 = new javax.swing.JScrollPane();           
            jTable1 = new javax.swing.JTable();            
            jScrollPane1.setFocusable(true);
            
  
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
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

         addKeyBindingToRequestFocusInPopUpWindow();
        pack();
    }
    
        private void addKeyBindingToRequestFocusInPopUpWindow() {
        
              textComp.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "Down released");
              textComp.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "Up released");
              textComp.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "ENTER released");
              textComp.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "ESCAPE released");
                   
               jTable1.setRowSelectionAllowed(true);
               jTable1.setColumnSelectionAllowed(false);
                  
           
              
           textComp.getActionMap().put("Down released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
            Logger.getGlobal().info("Down  and RowCont: "+jTable1.getRowCount());
                                                         
          
              
                     int t = getNextSelectionIndex(1);
                     jTable1.setRowSelectionInterval(0,t );   
                     jTable1.scrollRectToVisible(jTable1.getCellRect(t, 0, true));
               
            }
        });
        
           textComp.getActionMap().put("Up released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
          ///  Logger.getGlobal().info("Up  and RowCont: "+jTable1.getRowCount());
            
      
                     int t = getNextSelectionIndex(0);
                     jTable1.setRowSelectionInterval(0, t);   
                     jTable1.scrollRectToVisible(jTable1.getCellRect(t, 0, true));
            }
        });
           
           
       textComp.getActionMap().put("ENTER released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
          //  Logger.getGlobal().info("ENTER  and RowCont: "+jTable1.getRowCount());
            
                if( jTable1.getSelectedRow()!=-1 && me.isVisible()){
                 setText();
                 me.setVisible(false);
                 CurrentRowSelection = 0;
                }
            
            }
        });    
       
       textComp.getActionMap().put("ESCAPE released", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                
         //   Logger.getGlobal().info("ESCAPE  and RowCont: "+jTable1.getRowCount());
 
            me.setVisible(false);
            CurrentRowSelection = 0;
            
            }
        });   
       
       
        }
    
    int getNextSelectionIndex(int dimension){
        

         int rCount = jTable1.getRowCount();
         int curSel = jTable1.getSelectedRow();
                          
        ///DOWN
        if (dimension == 1) {

            if (curSel < 0) {
                curSel = 0;
                return 0;
            } else if ((curSel > rCount) && curSel >= 0) {
                curSel = rCount - 1;
                return curSel;
            } else {
                curSel++;
            }
        } else
        ///UP
        if (dimension == 0) {

            if (curSel < 0) {
                curSel = 0;
                return 0;
            } else {
                curSel--;
            }
        }

         return curSel;
    }
        
        
    public void SetListData(String [] list) {
     
        jTable1.setModel(new CompliteTableModel(list));
     
    }
    
    private void jTable1KeyReleased(KeyEvent evt) {
            
     /*   if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            this.setFocusableWindowState(false);
            this.setFocusable(false);
            setText();
            evt.consume();
            this.setVisible(false);
           
           
        }*/
       //   this.setVisible(false);
     }
    
    public void initTableSelection() {
 
      //  this.requestFocusInWindow();
      
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
