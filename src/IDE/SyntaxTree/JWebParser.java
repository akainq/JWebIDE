/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import IDE.SyntaxTree.types.FunctionDeclaration;
import java.io.StringWriter;
import static java.lang.System.out;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.tree.DefaultMutableTreeNode;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.IdentNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.Node;
import jdk.nashorn.internal.ir.ObjectNode;
import jdk.nashorn.internal.ir.PropertyNode;
import jdk.nashorn.internal.ir.Statement;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
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
            
    
         // jdk.nashorn.internal.ir.FunctionCall
        
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
             ScriptObjectMirror RootBody =  (ScriptObjectMirror) ((ScriptObjectMirror)((ScriptObjectMirror)sss).get("body")).get("0");
             
             DefaultMutableTreeNode root = new DefaultMutableTreeNode("");             
             getTree(RootBody,0,root);
             
             return root;
         } catch (ScriptException | NoSuchMethodException ex) {
             Logger.getLogger(JWebParser.class.getName()).log(Level.SEVERE, null, ex);
         }
         return null;
    }
    
    public DefaultMutableTreeNode createObjectTree(){
      return getTreeNode(new DefaultMutableTreeNode(""));
    }
    
    
    public void getTree(AbstractJSObject node , int deep, DefaultMutableTreeNode rootNode){
  
             ScriptObjectMirror  child_scriptObjectMirror = null;
         
              //if(node.isFunction())
             for(String key: node.keySet()){
                      
                    Object scriptObjectMirror =  node.getMember(key);
                    Object type =  node.getMember("type");
                    Object value =  node.getMember("value");
                    String tree = "";
                    //for(int i=1; i<deep;i++) tree+="\t";
                    
                   //
                  //  System.out.println( tree+" "+key+" "+scriptObjectMirror);
               
                   DefaultMutableTreeNode child = new DefaultMutableTreeNode(); 
                     //    if("Identifier".equals(type)||"FunctionExpression".equals(type)||"ThisExpression".equals(type)) { 
                   
                          //   if(scriptObjectMirror.getClass() == FunctionDeclaration)
                   
                           //  continue;
                     //  }
                                                             
                       if("FunctionDeclaration".equals(type)){
                       //FunctionDeclaration  funcDec = new FunctionDeclaration(node);
                       
                         //   AbstractJSObject dd = (AbstractJSObject)funcDec.Body;
                         //   String typeS = (String) dd.getMember("type");
                         rootNode.setUserObject(((AbstractJSObject)node.getMember("id")).getMember("name"));
                         //  rootNode.setUserObject(node.getClassName());
                       }
                      if(key.equals("body"))
                      {
                           //  getTree((ScriptObjectMirror)scriptObjectMirror, deep++,rootNode); 
                           //  return;
                      } else 
                        child.setUserObject(scriptObjectMirror);                                    
                             child.setAllowsChildren(true);
                             rootNode.add(child);
                         
                     
                         
        if((scriptObjectMirror!=null)&&(
           //(scriptObjectMirror.getClass() == Object.class)||
           (scriptObjectMirror.getClass() == ScriptObjectMirror.class)
           //(scriptObjectMirror.getClass() == Array.class)
                )){
                                      
            
                        
                            getTree((ScriptObjectMirror)scriptObjectMirror, deep++,child); 
                         
                    }else {
                    
                        
                    
                    }             
                 
                        
               }
             
             
       }
    
    
    public DefaultMutableTreeNode getTreeNode(DefaultMutableTreeNode rootNode){
        
    	Stack<DefaultMutableTreeNode> i = new Stack<>();    
      //  DefaultMutableTreeNode subroot = new DefaultMutableTreeNode("subroot");
      //  rootNode.add(subroot);
        i.push(rootNode);
         FunctionNode root = parse();
             LexicalContext lc = new LexicalContext();
         root.accept(new NodeVisitor(lc) {

             
                @Override
                public boolean enterFunctionNode(FunctionNode functionNode) {

                    if( ! functionNode.isProgram() ) {
        			DefaultMutableTreeNode outlineItem;
                                List<IdentNode> parameters = functionNode.visitParameters(this);
        			if( functionNode.isAnonymous() ) {
        				String name = ((IdentNode)functionNode.getIdent().accept(this)).getName();
        				if( name.contains(":") ) {
        					outlineItem = new DefaultMutableTreeNode("<anonymous>");
                                                for(IdentNode param: parameters){
                                                
                                                }
        				} else {
        					outlineItem = new DefaultMutableTreeNode(name);
                                                for(IdentNode param: parameters){
                                                   outlineItem.setUserObject(outlineItem.getUserObject()+"("+param.getMostOptimisticType().getDescriptor()+")");
                                                }
        				}
        			} else {
        				outlineItem = new DefaultMutableTreeNode(((IdentNode)functionNode.getIdent().accept(this)).getName());
                                             for(IdentNode param: parameters){
                                                
                                            }
        			}

        			i.peek().add(outlineItem);
        			i.push(outlineItem);
        		}

                    return super.enterFunctionNode(functionNode); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Node leaveFunctionNode(FunctionNode functionNode) {
                        if( ! functionNode.isProgram() ) {
                              i.pop();
                        }
                    return super.leaveFunctionNode(functionNode); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public Node leaveObjectNode(ObjectNode objectNode) {
                    DefaultMutableTreeNode outlineItem = new DefaultMutableTreeNode("ttt");
                    	i.peek().add(outlineItem);
        		i.push(outlineItem);
                    return super.leaveObjectNode(objectNode); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public boolean enterObjectNode(ObjectNode objectNode) {
                       i.pop();
                    return super.enterObjectNode(objectNode); //To change body of generated methods, choose Tools | Templates.
                }

              
                
             
               
                
         
         });
        
         return i.peek();
    
    }
    
  
}
