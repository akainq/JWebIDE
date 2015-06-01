/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

/**
 *
 * @author kuznetsov
 */
public class ExpNode  {
     
    String  operator;
    ExpNode operand1;
    ExpNode operand2;
    
  public ExpNode(String data){
    
      this.operator = data;
      operand1 = operand2 = null;
    
    }
  
   public ExpNode(String operator, ExpNode operand1, ExpNode operand2){    
      this.operator = operator;
      this.operand1 = operand1;
      this.operand2 = operand2;
    
    }
}
