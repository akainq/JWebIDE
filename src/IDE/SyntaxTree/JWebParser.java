/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import IDE.SyntaxTree.ASyntaxTree.STNode;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.tree.DefaultMutableTreeNode;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

/**
 *
 * @author kuznetsov
 */
public class JWebParser {
 
     jdk.nashorn.internal.parser.Parser nashornParser;
     StringWriter OutWriter = new StringWriter();
     StringWriter ErrorWriter = new StringWriter();
     ErrorManager em = new ErrorManager();
     ScriptEnvironment env;
     String strSource;
    
    public JWebParser(String source) {
 
      strSource = source;
 
         Options opt = new Options("nashorn");
             opt.set("anon.functions", true);
          //   opt.set("parse.only", true);
             opt.set("scripting", true);
             
          Context context = new Context(opt, em, Thread.currentThread().getContextClassLoader());
          Context.setGlobal(new Global( context ));
          Source textSource =   Source.sourceFor("mySource", source);
          
          env = context.getEnv();//new ScriptEnvironment(opt, new PrintWriter(OutWriter), new PrintWriter(ErrorWriter) );
                                        
         nashornParser =  new jdk.nashorn.internal.parser.Parser(env,textSource,em);   
     
	
        
    }
    
    public JWebParser(){}
    
    public FunctionNode parse(){
 
      return nashornParser.parse();
 
       
    }
        
    public DefaultMutableTreeNode createObjectTree(String src){
         try {
             ScriptEngineManager engineManager =   new ScriptEngineManager();
             NashornScriptEngine engine =  (NashornScriptEngine) engineManager.getEngineByName("nashorn");
             engine.eval("load(\"nashorn:parser.js\")");
             ScriptObjectMirror sss =  (ScriptObjectMirror) engine.invokeFunction("parse", src);
             ScriptObjectMirror RootBody =  (ScriptObjectMirror) ((ScriptObjectMirror)sss.get("body")).get("0");
             
             DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");             
             getTree(RootBody,0,root);
             
             return  root;
         } catch (ScriptException | NoSuchMethodException ex) {
             Logger.getLogger(JWebParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    

    public void getTree(ScriptObjectMirror node , int deep, DefaultMutableTreeNode rootNode){
  
             ScriptObjectMirror  child_scriptObjectMirror = null;
         
             for(String key: node.keySet()){
                      
                    Object scriptObjectMirror =  node.get(key);
                    Object type =  node.get("type");
                    String tree = "";
                    //for(int i=1; i<deep;i++) tree+="\t";
                    
                   //
                  //  System.out.println( tree+" "+key+" "+scriptObjectMirror);
                   DefaultMutableTreeNode child = new DefaultMutableTreeNode(); 
                         if("Identifier".equals(type)||"FunctionExpression".equals(type)||"ThisExpression".equals(type)) { 
                             
                             child.setUserObject(scriptObjectMirror);      
                              
                             rootNode.add(child);
                             continue;
                       }
                         
                    if((scriptObjectMirror!=null)&&((scriptObjectMirror.getClass() == Object.class)||
                                                    (scriptObjectMirror.getClass() == ScriptObjectMirror.class)||
                                                    (scriptObjectMirror.getClass() == Array.class))){
                        
                        if(scriptObjectMirror.getClass() == ScriptObjectMirror.class){                                                    
                            getTree((ScriptObjectMirror) ((ScriptObjectMirror)scriptObjectMirror), deep++,child);                           
                         }
                          else
                         {                        
                            getTree((ScriptObjectMirror)scriptObjectMirror, deep++,child); 
                         }
                    }             
                 
                        
               }
             
             
       }
    
    
  
}
