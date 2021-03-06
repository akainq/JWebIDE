/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import static IDE.JWebIDE.readServerFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.Symbol;
import jdk.nashorn.internal.ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sun.nio.ch.DirectBuffer;

/**
 *
 * @author kuznetsov
 */
public class ParserTest {
    
    public ParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
   public static String readServerFile(File file) throws FileNotFoundException, IOException {
        
      FileInputStream inputStream = new FileInputStream(file);
      final FileChannel inChannel = inputStream.getChannel();   
      
      String tmpContent = "";
      
                MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0 ,inChannel.size());
                buffer.load();        
                byte [] bb = new byte[new Long(inChannel.size()).intValue()];                
                buffer.get(bb);
                tmpContent = new String(bb);                           
               ((DirectBuffer) buffer).cleaner().clean();  
                
        return tmpContent;
            
}
    /**
     * Test of parse method, of class Parser.
     */
    @Test
    public void testParse() {
        try {

            String input = readServerFile(new File("c:\\Projects\\JWeb\\www\\Controller\\indexController.jap"));
            ExpNode expResult = null;
            JWebParser result = new  JWebParser(input);
            FunctionNode fNode = result.parse();
            
                //Logger.logMsg(Level.ALL.intValue(),);
         // System.out.println( fNode.getName());
         // System.out.println( fNode.getBody());
          LexicalContext lc = new LexicalContext();
         
         fNode.accept(new NodeVisitor(lc) {

                @Override
                public boolean enterFunctionNode(FunctionNode functionNode) {
                    
              if( ! functionNode.isProgram() ) {
                  
                  if( functionNode.isAnonymous() ) {
                      
                        String name = ((IdentNode)functionNode.getIdent().accept(this)).getName();
                        if( name.contains(":") ) {
        				 
                                                  System.out.println("<anonymous> FUNCTION_ICON");
        				} else {
        					  System.out.println(name +" METHOD_ICON");
        				}
  
                
                  } else 
                  {
                       
                        System.out.println(((IdentNode)functionNode.getIdent().accept(this)).getName());
                  }
              }
                    
                    return super.enterFunctionNode(functionNode); //To change body of generated methods, choose Tools | Templates.
                }
         
                
                
             
         
         });
         Iterator<FunctionNode> sss = lc.getFunctions();
         IdentNode ident = fNode.getIdent();
          
            // assertEquals(expResult, result);
            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
        } catch (IOException ex) {
            Logger.getLogger(ParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
