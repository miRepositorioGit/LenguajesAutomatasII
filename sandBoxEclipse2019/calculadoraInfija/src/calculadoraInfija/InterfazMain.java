package calculadoraInfija;

import java.io.BufferedReader;
import java.io.InputStreamReader;



/** Interfaz de la clase Calculadora. Notación INFIJA. 
 * 			Calcula expresiones aritméticas con aridad 2.
 * Solicita a nivel de consola la introducción de:
 *  
 * 1/4 PRIMER OPERANDO.  Digitado en número entero sin signo, con una resolución de hasta 4 digitos. 
 *       La solicitud del primer operando es, digito por digito, comenzando por las unidades posicionales 
 *       de mayor peso.
 *       			Por ejemplo.   Para introducir la magnitud entera : 1023 
* 										[ 0 ]1                     presionar ENTER
* 										[ 1 ]0
* 										[ 10 ]2
*										[ 102 ]3
* 										[ 1023 ]
 *  2/4 OPERADOR ARITMÉTICO. Son válidos los simbolos aritméticos binarios, listados en la 
 *  siguiente relación:
 *   						Operador			Descripción
 *   							*                  multiplicación aritmética.
 *   							\					división aritmética.
 *   						   +					suma aritmética.
 *   						   -					resta aritmética.  
 *  3/4 SEGUNDO OPERANDO. Digitado en número entero sin signo, con una resolución 
 *  de hasta 4 digitos.
 *  4/4 ASIGNACIÓN. El simbolo de asignación simple (=) .
 *  
 *  Por ejemplo. 
 *  Ejecutar la interpretación de la expresión 1023 + 1023, se tiene que:
 *  						[ 0 ]c
*   						[ 0 ]1
*							[ 1 ]0
*							[ 10 ]2
*							[ 102 ]3
*							[ 1023 ]+ 
*							[ 0 ]1
*							[ 1 ]0
*							[ 10 ]2
*							[ 102 ]3
*							[ 1023 ]=
*							[ 2046 ]
 * 
 * @author MartinCordero. 
 * Fuentes: 
 * [1]  	 D. Poo, D. Kiong, S. Ashok . Object-Oriented Programming and Java. Springer. Second edition. 2008. 
 * 			 Implementation in Java.
 *			 https://books.google.com.mx/books?id=KjyzGJdnd3gC&pg=PA42&lpg=PA42&dq=void+binaryOperation(char+op)+%7B+keep+%3D+value;+//+keep+first+operand+value+%3D+0;+//+initialize+and+get+ready+for+second+operand+toDo+%3D+op;+%7D&source=bl&ots=4K7pv8H4QZ&sig=ACfU3U03CpA5C8d1jogz9mhBRFS_mvR5Dg&hl=es&sa=X&ved=2ahUKEwi6sObtxPLkAhUC0KwKHTuRAUkQ6AEwAHoECAkQAQ#v=onepage&q&f=true
 *
 *	[2]		Java™ Platform. Standard Ed. 7. Class BufferedReader
 *			Class BufferedReader.
 *			https://docs.oracle.com/javase/7/docs/api/java/io/BufferedReader.html#BufferedReader(java.io.Reader,%20int)	
 *
 *  [3]    R. Caballero. Dpto. Sistemas Informáticos y Programación. Universidad Complutense de Madrid
 *          Uso de JavaDoc.
 *          http://gpd.sip.ucm.es/rafa/docencia/programacion/tema1/javadoc.html
 *          
 *  [4]    Geeky Theory.   Cómo leer un fichero en Java. Copyright © 2019
 *  		https://geekytheory.com/como-leer-un-fichero-en-java        
 *
 */


public class InterfazMain {
	
	BufferedReader bufferedReader;  
	Calculadora objCalculadora;
	String pathTexto = "textoInformacion.txt";
	
	/** lee un flujo de bytes y retorna un flujo de caracteres, 
	 *  lee un flujo de caracteres, retorna un buffer de caracteres
	 *  */
	public InterfazMain(Calculadora cal)  {
		InputStreamReader inputStreamReader = new InputStreamReader(System.in);
		bufferedReader = new BufferedReader(inputStreamReader) ;
		objCalculadora = cal;
	}
	
	void run() throws Exception {
		for (;;) {
			System.out.print("[ " + objCalculadora.display() + " ]");
			String inputData = bufferedReader.readLine();
			if (inputData == null) 
				{
				System.out.print("Nulo"); 
				break;
				} 
			if (inputData.length() > 0) 
				{
					char charInput = inputData.charAt(0);
					
					/**  Pila de métodos */
						if (charInput == '+')       objCalculadora.add();   
						else if (charInput == '-') objCalculadora.subtract();
						else if (charInput == '*') objCalculadora.multiply();
						else if (charInput == '/') objCalculadora.divide();
						else if (charInput >= '0' && charInput <= '9') objCalculadora.digit(charInput - '0');
						else if (charInput == '=') objCalculadora.compute();
						/* miselaneaos */
						else if (charInput == 'c' || charInput == 'C') objCalculadora.clear();
						else if (charInput == 's' || charInput == 'S')  objCalculadora.exitProgram();
						else if (charInput == 'i' || charInput == 'I')  objCalculadora.muestraInformacion(pathTexto);
						else if (charInput == 'q' || charInput == 'Q')  objCalculadora.limpiaPantalla();
				}
		}
	}
	public static void main(String[] args) throws Exception {
		Calculadora objCalculadora = new Calculadora();
		InterfazMain estado = new InterfazMain(objCalculadora) ;
		estado.run();
	}
}
