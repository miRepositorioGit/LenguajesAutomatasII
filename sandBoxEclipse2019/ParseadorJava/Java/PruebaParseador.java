/**
 *Programa realizado por Alexander Borbón Alpízar
 *profesor del Instituto Tecnológico de Costa Rica
 *
 *Para comentarios o sugerencias esccribir a
 *aborbon@itcr.ac.cr
 *fuente: https://tecdigital.tec.ac.cr/revistamatematica/ContribucionesV7_n2_2006/Parseador/index.html
 */

import java.awt.Button;
import java.awt.Event;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


/** Clase códificada en Java.  Muestra como traducir una expresión matemática en 
 * notación infija a notación postfija.
 * @author Martin
 *
 */

@SuppressWarnings({ "unused", "serial" })
public class PruebaParseador extends java.applet.Applet {
	String pathTexto = "textoInformacion.txt";
	double    valor = 0; //Valor en el que se va a evaluar
	/**
	 * @param miparser objeto Parseador.  Parsea una expresion matemática.
	 * @param inputexpresion recupera expresión matemática.
	 * @param inputvalor recupera la magnitud de la variable de la expresión matemática.
	 * @param  boton ejecuta evaluación de expresión matemática.
	 *  @param outputparseo recupera y muestra  la expresión en notación postfija . 
	 *  @param outputevaluar recupera u muestra el resultado
	 * */
	Parseador miparser = new Parseador();  //Constructor del parseador
	String       expresion = new String(); //Expresión a parsear
	TextField inputexpresion = new TextField("x + 5"); //Textfield donde se digita la expresión a parsear
	TextField inputvalor = new TextField("0",5); //Textfield donde se digita el valor a evaluar en la expresión
	Button    boton = new Button("Evaluar la expresión"); //Botón para evaluar
	Button    botonInfo = new Button ("Información");
	TextField outputparseo = new TextField("          "); //Resultado de parsear la expresión
	TextField outputevaluar = new TextField("         "); //Resultado de la evaluación en la expresión
	Label       info = new Label(" ", Label.CENTER);      //Label donde se dan los errores
	
	/**
	 * @param init() se invoca por el web browser, viwer applet. 
	 *  Inicializa y carga parametros en applet para el sistema
	 */
	public void init(){ //Todo se pone en el applet
		add(inputexpresion);
		add(inputvalor);
		add(boton);
		add(botonInfo);					// despliega información
		add(outputparseo);
		add(outputevaluar);
		add(info);
	}//init

	/**
	 * @param evt.target administra el evento tipo button
	 * @param expresion recupera expresion notacion postfija.
	 * @param valor, Real, recupera incognita de la expresion postfija   
	 */
		
	public boolean action(Event evt, Object arg){
		if (evt.target instanceof Button){ //Si se apretó el botón
			try{
				info.setText(""); //Se pone el Label de los errores vacío
				expresion = inputexpresion.getText(); //Se lee la expresión
				valor = Double.valueOf(inputvalor.getText()).doubleValue(); //Se lee el valor a evaluar
				outputparseo.setText(miparser.parsear(expresion)); //Se parsea la expresión
				outputevaluar.setText(""+redondeo(miparser.f(valor),5)); //Se evalúa el valor y se redondea
			}catch(Exception e){ //Si hubo error lo pone en el Label correspondiente
				info.setText(e.toString());
			}
		}//if del botón
		if (evt.target instanceof Button) {
			//System.out.print("Información");
			try {
				muestraInformacion(pathTexto);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return true;
	}//action

	/*
	 *Se redondea un número con los decimales dados
	 */
	private double redondeo(double numero, int decimales){
		return ((double)Math.round(numero*Math.pow(10,decimales)))/Math.pow(10,decimales);
	}

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
	
	
	
	
}//PolCero

