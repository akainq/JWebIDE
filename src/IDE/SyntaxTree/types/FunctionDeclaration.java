/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree.types;

import java.util.Collection;
import java.util.Set;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 *
 * @author Aleksey
 */


public class FunctionDeclaration extends IJSObject{
    
  public  String type;
  public  ID id;
  public  AbstractJSObject  params;
  public  AbstractJSObject  defaults;
  public  Object rest;
  public  Object Body;
  public  boolean generator;
  public  boolean expression;

    public FunctionDeclaration(AbstractJSObject node ) {
        
        this.type  = (String)node.getMember("type");
        this.id = new ID((ScriptObjectMirror)node.getMember("id"));
        this.params = (AbstractJSObject) node.getMember("params");
        this.defaults = ( AbstractJSObject )node.getMember("defaults");
        this.rest = node.getMember("rest");
        this.Body =   node.getMember("body");
        this.generator = (boolean)node.getMember("generator");
        this.expression = (boolean)expression;
        
    }

  
  
  
    @Override
    public String toString() {
 
          return id.name.toString();
    }


   
    
}
