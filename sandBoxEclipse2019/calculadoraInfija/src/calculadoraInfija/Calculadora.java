package calculadoraInfija;
/** Implementa una calculadora aritm�tica, empleando dos registros.
 * 
 * Fuente: 
 * [1] D. Poo, D. Kiong, S. Ashok . Object-Oriented Programming and Java. Springer. Second edition. 2008. 
 * Implementation in Java.
 * https://books.google.com.mx/books?id=KjyzGJdnd3gC&pg=PA42&lpg=PA42&dq=void+binaryOperation(char+op)+%7B+keep+%3D+value;+//+keep+first+operand+value+%3D+0;+//+initialize+and+get+ready+for+second+operand+toDo+%3D+op;+%7D&source=bl&ots=4K7pv8H4QZ&sig=ACfU3U03CpA5C8d1jogz9mhBRFS_mvR5Dg&hl=es&sa=X&ved=2ahUKEwi6sObtxPLkAhUC0KwKHTuRAUkQ6AEwAHoECAkQAQ#v=onepage&q&f=true 
 * @author MartinCordero
 *
 *			Implementa una calculadora con cuatro funciones aritm�ticas.
 * 			Implementa una calculadora aritm�tica en modo infijo. 
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
	 * recupera operador, especifica tipo de operaci�n aritm�tica
	 * 
	 * @param op operador aritm�tico asociado
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
	} // invova la ejecuci�n de una suma aritm�tica

	void subtract() {
		binaryOperation('-');
	} // invova la ejecuci�n de resta aritm�tica

	void multiply() {
		binaryOperation('*');
	} // invova la ejecuci�n de multiplicaci�n aritm�tica

	void divide() {
		binaryOperation('/');
	} // invova la ejecuci�n de una divisi�n aritm�tica

	/**
	 * ejecuta operador aritm�tico entre el primer operando y segundo operando.
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
	 * inicializa en cero registros para pr�ximo c�lculo
	 */
	@SuppressWarnings("null")
	void clear() {
		value = 0; // calculator register. first operand
		keep = 0; // calculator register. second operand.
	}

	/**
	 * convierte un caracter en un entero sin signo acumula digitos ingresados.
	 * 
	 * @param x �ltimo digito
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
	 * muestra informaci�n de la ejecucu�n del programa infijo
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
