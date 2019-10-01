package calculadoraInfija;
/** Implementa una calculadora aritmética, empleando dos registros.
 * 
 * Fuente: 
 * [1] D. Poo, D. Kiong, S. Ashok . Object-Oriented Programming and Java. Springer. Second edition. 2008. 
 * Implementation in Java.
 * https://books.google.com.mx/books?id=KjyzGJdnd3gC&pg=PA42&lpg=PA42&dq=void+binaryOperation(char+op)+%7B+keep+%3D+value;+//+keep+first+operand+value+%3D+0;+//+initialize+and+get+ready+for+second+operand+toDo+%3D+op;+%7D&source=bl&ots=4K7pv8H4QZ&sig=ACfU3U03CpA5C8d1jogz9mhBRFS_mvR5Dg&hl=es&sa=X&ved=2ahUKEwi6sObtxPLkAhUC0KwKHTuRAUkQ6AEwAHoECAkQAQ#v=onepage&q&f=true 
 * @author MartinCordero
 *
 *			Implementa una calculadora con cuatro funciones aritméticas.
 * 			Implementa una calculadora aritmética en modo infijo. 
 * 			Con los registros {keep,value} para almacenar operados 
 * 			
 * 
 * 		Por ejemplo:
 * 							1
*       					3
*       					+
*       					1
*       					1
*       					=
*      						24 
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Calculadora {

	int value; // calculator register. first operand
	int keep; // calculator register. second operand.
	char toDo; // operator register

	/**
	 * recupera operador, especifica tipo de operación aritmética
	 * 
	 * @param op operador aritmético asociado
	 */
	void binaryOperation(char op) {
		keep = value; // keep first operand
		value = 0; // initialize and get ready for second operand
		toDo = op; // record the action to be associated
	}

	/**
	 * asigna operador binario (aridad 2)
	 */
	void add() {
		binaryOperation('+');
	} // invova la ejecución de una suma aritmética

	void subtract() {
		binaryOperation('-');
	} // invova la ejecución de resta aritmética

	void multiply() {
		binaryOperation('*');
	} // invova la ejecución de multiplicación aritmética

	void divide() {
		binaryOperation('/');
	} // invova la ejecución de una división aritmética

	/**
	 * ejecuta operador aritmético entre el primer operando y segundo operando.
	 */
	void compute() {
		if (toDo == '+')
			value = keep + value;
		else if (toDo == '-')
			value = keep - value;
		else if (toDo == '*')
			value = keep * value;
		else if (toDo == '/')
			value = keep / value;
		keep = 0;
	}

	/**
	 * inicializa en cero registros para próximo cálculo
	 */
	@SuppressWarnings("null")
	void clear() {
		value = 0; // calculator register. first operand
		keep = 0; // calculator register. second operand.
	}

	/**
	 * convierte un caracter en un entero sin signo acumula digitos ingresados.
	 * 
	 * @param x último digito
	 */
	void digit(int x) {
		value = value * 10 + x;
	}

	/**
	 * retorna el resultado
	 * 
	 * @return resultado registrado.
	 */
	int display() {
		return (value);
	}

	/**
	 * salir del programa
	 * 
	 */
	void exitProgram() {
		clear();
		System.exit(0);
	};

	/**
	 * muestra información de la ejecucuón del programa infijo
	 */
	void muestraInformacion(String arg0) throws FileNotFoundException, IOException {
		String textoInformacion = " ";
		FileReader myfileReader = new FileReader(arg0);
		BufferedReader mybufferReader = new BufferedReader(myfileReader);
		while ((textoInformacion = mybufferReader.readLine()) != null) {
			System.out.println(textoInformacion);
		}
		mybufferReader.close();
	}

	/**
	 * limpia consola de comandos
	 */
	void limpiaPantalla() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			/* No hacer nada */
		}
	}

	Calculadora() {
		clear();
	}
}
