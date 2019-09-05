/** muestra como implementar en crudo la interpretación de un texto
 *                                      Proceso de generacion-compilación de clases
 *    >antlr Calc.g4                            |  Calc*.java <- antlr 
 *    >antlr Calc.g4 -visitor                   |  Calc*Visitor.java <- antlr -visitor
 *    >javac *.java                             |  Calc*.class <- javac  *.java
 *                                       Identificación de tokens y árbol 
 *    >grun Calc start test.txt -tokens          |  muestra tokes encontrados
 *    >grun Calc start test.txt -gui             |  muestra árbol
 * 	  >java Calc test.txt                 Proceso de interpretación de archivo de lenguaje          
 *   fuente:
 *   www.antlr.org 
 *   Ahmed T. Ali Publicado el 11 ago. 2016
 *   3. Building a simple Calculator using a Visitor (Part 1)
 *   https://www.youtube.com/watch?v=YHO_GRwnU90
 *    
 */

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class Calc{
	/** muestra como interelacionar las clases generadas por ANTRL
	*   rawTokens: tokens del archivo de entrada
	*/
	
    public static void main(String args[])throws Exception {
	     
		ANTLRFileStream rawTokens = new ANTLRFileStream(args[0]);
		
				System.out.println("");
				System.out.println("\t Tokens encontrados:" + rawTokens); // muestra tokens del archivo de entrada
				System.out.println("");
				
        CalcLexer lexer = new CalcLexer(rawTokens);
		
        CommonTokenStream  tokenStream = new CommonTokenStream(lexer);
						
        CalcParser parser = new CalcParser(tokenStream);
		
        ParseTree tree = parser.start();

	    System.out.println("\t Interpretacion: " + new MyVisitor().visit(tree));

    }
}
class MyVisitor extends CalcBaseVisitor<Integer>{

    @Override 
    public Integer visitStart(CalcParser.StartContext ctx) {
        return visit(ctx.expr());
    }

    /**muestra la interpretación del archivo de entrada
     * retorna el resultado de la interpretación
     * @return entero signado resultado de la interpretación del archivo fuente
     */
    @Override 
    public Integer visitBinaryOp(CalcParser.BinaryOpContext ctx) 
		{
		
			int lhs = visit(ctx.expr(0)); // left  hand size
			int rhs = visit(ctx.expr(1)); // rigth hand size

			String op = ctx.op().getText();    
        
			int  res = 0;
				if(op.equals("+")){
					res = lhs + rhs;
				}else if(op.equals("-")){
					res = lhs - rhs;
				}else if(op.equals("/")){
					res = lhs / rhs;
				}else if(op.equals("*")){
					res = lhs * rhs;
        }

        return res; 
    }

    @Override 
    public Integer visitNum(CalcParser.NumContext ctx) 
		{ 
			System.out.println("Tokens tipo numero: " + ctx.Num().getText());   // muestra los componentes léxicos tipo NUM
			return Integer.parseInt(ctx.Num().getText());
        }   

	   
    @Override 
    public Integer visitOp(CalcParser.OpContext ctx) { 
        return visitChildren(ctx); 
    }
}
