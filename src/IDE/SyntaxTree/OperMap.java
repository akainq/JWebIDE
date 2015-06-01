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
public class OperMap{

   private Integer  precedence;
   private OpAssociativity associativity;
        
   public OperMap( Integer  precedence, OpAssociativity associativity){

    this.precedence = precedence;
    this.associativity = associativity;
   
   
   }

    /**
     * @return the precedence
     */
    public Integer getPrecedence() {
        return precedence;
    }

    /**
     * @return the associativity
     */
    public OpAssociativity getAssociativity() {
        return associativity;
    }
}
