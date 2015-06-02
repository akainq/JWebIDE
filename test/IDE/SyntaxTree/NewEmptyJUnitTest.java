/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.AbstractMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import sun.nio.ch.DirectBuffer;
import static sun.nio.ch.IOStatus.EOF;

/**
 *
 * @author Aleksey
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void hello() throws ScriptException {
    //jdk.nashorn.api.
        try {
            String input = readServerFile(new File("c:\\Projects\\JWeb\\www\\Controller\\indexController.jap"));
            ScriptEngineManager engineManager =   new ScriptEngineManager();
            NashornScriptEngine engine =  (NashornScriptEngine) engineManager.getEngineByName("nashorn");
          engine.eval("load(\"nashorn:parser.js\")");
           // engine.eval("var tree = Java.type(\"com.sun.source.tree.CompilationUnitTree\")");
          //  engine.eval("tree = parse(\"function Test(){return 1;}\")");
       
           
        
                 ScriptObjectMirror sss =  (ScriptObjectMirror) engine.invokeFunction("parse", input);                  
                 ScriptObjectMirror RootBody =  (ScriptObjectMirror) ((ScriptObjectMirror)sss.get("body")).get("0");
                 
                 Parse(RootBody,0);
                 
                 engine.put("fff", sss);
          
                 
                  engine.eval("var empty = [];"
                            + "for(var index in fff['body'][0]){    "                          
                            + "  /*empty.push(index);*/ "
                            + "for(var index2 in fff['body'][0][index]){empty.push(index2);}"
                            + "}"+
                              " ");
                String sss2 = (String) engine.eval("JSON.stringify( empty)");
                
                
                
           //  com.sun.source.tree.CompilationUnitTree
            // sss
            // engine.eval("var ast = parse(\"function Test(){ return 1;}\")");
           System.out.println(  sss2);
        } catch (IOException | NoSuchMethodException ex) {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
       public void Parse(ScriptObjectMirror node , int deep){
  
             ScriptObjectMirror  child_scriptObjectMirror = null;
                          
             for(String key: node.keySet()){
                      
                    Object scriptObjectMirror =  node.get(key);
                    Object type =  node.get("type");
                    String tree = "";
                    for(int i=1; i<deep;i++) tree+="\t";
                    
                   // if("Identifier".equals(type)||"FunctionExpression".equals(type)||"ThisExpression".equals(type)) //ThisExpression
                    System.out.println( tree+" "+key+" "+scriptObjectMirror);
                                    
                    if((scriptObjectMirror!=null)&&((scriptObjectMirror.getClass() == Object.class)||
                                                    (scriptObjectMirror.getClass() == ScriptObjectMirror.class)||
                                                    (scriptObjectMirror.getClass() == Array.class))){
                        
                        if(scriptObjectMirror.getClass() == ScriptObjectMirror.class){                                                    
                           Parse( (ScriptObjectMirror) ((ScriptObjectMirror)scriptObjectMirror), deep++);                           
                         }
                          else
                         {                        
                            Parse((ScriptObjectMirror)scriptObjectMirror, deep++); 
                         }
                    }             
                 
             
               }
             
             
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
}
