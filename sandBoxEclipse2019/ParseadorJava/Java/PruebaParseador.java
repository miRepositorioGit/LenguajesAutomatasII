/**
 *Programa realizado por Alexander Borb�n Alp�zar
 *profesor del Instituto Tecnol�gico de Costa Rica
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


/** Clase c�dificada en Java.  Muestra como traducir una expresi�n matem�tica en 
 * notaci�n infija a notaci�n postfija.
 * @author Martin
 *
 */

@SuppressWarnings({ "unused", "serial" })
public class PruebaParseador extends java.applet.Applet {
	String pathTexto = "textoInformacion.txt";
	double    valor = 0; //Valor en el que se va a evaluar
	/**
	 * @param miparser objeto Parseador.  Parsea una expresion matem�tica.
	 * @param inputexpresion recupera expresi�n matem�tica.
	 * @param inputvalor recupera la magnitud de la variable de la expresi�n matem�tica.
	 * @param  boton ejecuta evaluaci�n de expresi�n matem�tica.
	 *  @param outputparseo recupera y muestra  la expresi�n en notaci�n postfija . 
	 *  @param outputevaluar recupera u muestra el resultado
	 * */
	Parseador miparser = new Parseador();  //Constructor del parseador
	String       expresion = new String(); //Expresi�n a parsear
	TextField inputexpresion = new TextField("x + 5"); //Textfield donde se digita la expresi�n a parsear
	TextField inputvalor = new TextField("0",5); //Textfield donde se digita el valor a evaluar en la expresi�n
	Button    boton = new Button("Evaluar la expresi�n"); //Bot�n para evaluar
	Button    botonInfo = new Button ("Informaci�n");
	TextField outputparseo = new TextField("          "); //Resultado de parsear la expresi�n
	TextField outputevaluar = new TextField("         "); //Resultado de la evaluaci�n en la expresi�n
	Label       info = new Label(" ", Label.CENTER);      //Label donde se dan los errores
	
	/**
	 * @param init() se invoca por el web browser, viwer applet. 
	 *  Inicializa y carga parametros en applet para el sistema
	 */
	public void init(){ //Todo se pone en el applet
		add(inputexpresion);
		add(inputvalor);
		add(boton);
		add(botonInfo);					// despliega informaci�n
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
		if (evt.target instanceof Button){ //Si se apret� el bot�n
			try{
				info.setText(""); //Se pone el Label de los errores vac�o
				expresion = inputexpresion.getText(); //Se lee la expresi�n
				valor = Double.valueOf(inputvalor.getText()).doubleValue(); //Se lee el valor a evaluar
				outputparseo.setText(miparser.parsear(expresion)); //Se parsea la expresi�n
				outputevaluar.setText(""+redondeo(miparser.f(valor),5)); //Se eval�a el valor y se redondea
			}catch(Exception e){ //Si hubo error lo pone en el Label correspondiente
				info.setText(e.toString());
			}
		}//if del bot�n
		if (evt.target instanceof Button) {
			//System.out.print("Informaci�n");
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
	 *Se redondea un n�mero con los decimales dados
	 */
	private double redondeo(double numero, int decimales){
		return ((double)Math.round(numero*Math.pow(10,decimales)))/Math.pow(10,decimales);
	}

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
	
	
	
	
}//PolCero

