/**
 *Clase realizada por Alexander Borbón Alpízar
 *Profesor de matemática del Instituto Tecnológico de Costa Rica
 *                 Parsea es analizar una cadea de sÃ­mbilos de acuerdo a las reglas de una gramÃ¡tica.
 *Clase que parsea una expresión matemática.
 * 
 *Para parsear se debe crear un objeto (Obj) de tipo Parseador.
 *Para parsear expr se escribe Obj.parsear(Expr).
 *La función devuelve un String con Expr en notación postfija, además
 *el programa también guarda de manera automática la última expresión parseada.
 *Para evaluar el número x en la expresión se utilizar Obj.f(x) para evaluar
 *en la última expresión o se puede pasar una expresion en notacon postfija
 *escribiendo Obj.f(exprEnPostfija, x).	 
 *
 *		parseador :Código en java que analiza una cadena de símbolos de acuerdo a las reglas de una gramática formal. *
 *La variable permitida es x.
 *La expresion puede contener las constantes pi y e.
 *Los operadores validos de la expresión son:
 *    OPERACIóN           OPERADOR
 *  suma                        +
 *  resta                       -
 *  multiplicación              *
 *  división                    /
 *  potencias                   ^
 *  módulo                      %
 *  paréntesis                  ( )
 *  logaritmo (base e)          ln( )
 *  logaritmo (base 10)         log( )
 *  valor absoluto              abs( )
 *  número aleatorio            rnd( )
 *  seno                        sen( )
 *  coseno                      cos( )
 *  tangente                    tan( )
 *  secante                     sec( )
 *  cosecante                   csc( )
 *  cotangente                  cot( )
 *  signo                       sgn( )
 *  arcoseno                    asen( )
 *  arcocoseno                  acos( )
 *  arcotangente                atan( )
 *  arcosecante                 asec( )
 *  arcocosecante               acsc( )
 *  arcocotangente              acot( )
 *  seno hiperbólico            senh( )
 *  coseno hiperbólico          cosh( )
 *  tangente hiperbólica        tanh( )
 *  secante hiperbólica         sech( )
 *  cosecante hiperbólica       csch( )
 *  cotangente hiperbólica      coth( )
 *  raices cuadradas            sqrt( )
 *  arcoseno hiperbólico        asenh( )
 *  arcocoseno hiperbólico      acosh( )
 *  arcotangente hiperbólica    atanh( )
 *  arcosecante hiperbólica     asech( )
 *  arcocosecante hiperbólica   acsch( )
 *  arcocotangente hiperbólica  acoth( )  
 *  redondeo                    round( )
 *
 *Algunos ejemplos de expresiones válidas son:
 *x+cos(3)*tan(x^(2*pi*x-1))/acos(1/2)
 *cosh(x)+abs(1-x^2)%3
 *
 *@author Alexander Borbón Alpízar
 * fuente: https://tecdigital.tec.ac.cr/revistamatematica/ContribucionesV7_n2_2006/Parseador/index.html
 */

//Clases importadas
import java.util.*;

public class Parseador{

	//VARIABLES PRIVADAS
	
	
	/** Guarda la ultima expresion que se tradujo a postfijo para  
	 *  poder evaluar en ella sin dar una nueva expresion
	 * @ultimaParseada  guarda la ultima expresion que se tradujo
	 */
	private String ultimaParseada;  
	
	//CONSTRUCTORES
	
	public Parseador(){
		ultimaParseada = "0";
	}
	//FUNCIONES PUBLICAS
	
	/**
	 *La funcion que parsea la expresion a notacion postfija.
	 *@param expresion El string con la expresion a parsear.
	 *@return Un String con la expresion parseada en notacion postfija.
	 *@exception SintaxException Error de escritura en la expresion.
	 */
	public String parsear(String expresion) throws SintaxException{
		Stack<String> PilaNumeros     = new Stack<String>();     //Pila donde se guardarón los números al parsear
		Stack<String> PilaOperadores = new Stack<String>(); //Pila donde se guardarón los operadores al parsear
		String expr = quitaEspacios(expresion.toLowerCase());  //La expresión sin espacios ni mayúsculas.
		String fragmento; //Guarda el fragmento de texto que se está utilizando en el momento (ya sea un número, un operador, una función, etc.)
		int pos = 0;      // pos marca la posicion del caracter que se este procesando actualmente en el String.
		int tamano = 0;  // define el tamano del texto que se procesa en ese momento.     
		byte cont = 1;    //contador indica el número de caracteres que se sacan del string en un momento indicado, este no puede ser más de seis (la función con más caracteres tiene seis)
		byte sizeExpresion = 6; // define tamano de la expres
		
		/**Este es un arreglo de Strings que guarda todas las 
		 * funciones y expresiones permitidas, incluso numeros, 
		 * y los acomoda en cada posicion de acuerdo a su tamano 
		 */
		final String funciones[]={"1 2 3 4 5 6 7 8 9 0 ( ) x e + - * / ^ %",
							"pi",
							"ln(",
							"log( abs( sen( sin( cos( tan( sec( csc( cot( sgn(",
							"rnd() asen( asin( acos( atan( asec( acsc( acot( senh( sinh( cosh( tanh( sech( csch( coth( sqrt(",
							"round( asenh( acosh( atanh( asech( acsch( acoth("};

		/**Todas las funciones que trabajan como 
		 * parentesis de apertura estan aqui.
		 */
		final String parentesis="( ln log abs sen sin cos tan sec csc cot sgn asen asin acos atan asec acsc acot senh sinh cosh tanh sech csch coth sqrt round asenh asinh acosh atanh asech acsch acoth";
		/**
		 *	Esta variable 'anterior' se utiliza para saber cual habia sido la ultima
		 *	expresion parseada y verificar si hay un error en la expresion o si hay
		 *	algun menos unario en la expresion, se utiliza:
		 *			0 : no ha parseado nada
		 *			1 : un numero (numero, pi, e, x)
		 *			2 : un operador binario (+ - * / ^ %)
		 *			3 : un parentesis (( sen( cos( etc.)
		 *			4 : cierre de parentesis ())
		 *Si no se ha parseado nada puede ser cualquier cosa menos (+ * / ^ %)
		 *Si el anterior fue un numero puede seguir cualquier cosa
		 *Si lo anterior fue un operador puede seguir cualquier cosa menos otro operador (con excepcion de -)
		 *Si lo anterior fue un parentesis puede seguir cualquier cosa menos (+ * / ^ %)
		 *Si lo anterior fue un cierre de parentesis debe seguir un operador, un número (en cuyo caso hay un por oculto) u otro paréntesis (también hay un por oculto)
		 */
		byte anterior = 0;
		
/**
 * Haga mientras la posicion sea menor al 
 * tamano del String 
 * (mientras este dentro del string)
 */
		try{
			while(pos<expr.length()){ 
				tamano=0;
				cont=1;

				/**  
				 * evalua si, la posicion actual más el contador es menor de la longitud del texto
				 * y si el fragmento de texto que está en alguna funciones y expresiones permitidas,
				 * incluso numeros; si esto pasa el siguiente texto que se tiene que procesar es de tamano cont 
				 * */ 

				while (tamano == 0 && cont <= sizeExpresion)
					{ 
							if(pos + cont <= expr.length() && 
										funciones[cont-1].indexOf(expr.substring(pos,pos+cont))!=-1)
										{
											tamano = cont;
										}
							cont++;
					}
				
				/** Evalua casos con respecto al tamano encontrado **/
				
				/** Si no encontro nada es por que hubo un error, 
				 * se pone la ultima parseada en cero y se lanza 
				 * una excepcion 
				 * */

				if (tamano == 0)
				{ 
					ultimaParseada = "0";
					throw new SintaxException("Error en la expresion");

					/** tamano UNO, expresión parseada tiene un número  * */
					
				}else if(tamano==1){ 

					/** Si es un numero se encarga de sacarlo completo */
					if(isNum(expr.substring(pos,pos+tamano)))
						{ 
							/** si hay una multiplicacion oculta */	
							if(anterior==1 || anterior==4){
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}

							/**Hagalo mientras lo que siga sea 
							 * un numero o un punto o una coma
							*/
						fragmento=""; //aqui se guardara el número sacado
						do{ 
							fragmento=fragmento+expr.charAt(pos);
							pos++;
						}while(pos<expr.length() 
								&& (isNum(expr.substring(pos,pos+tamano))
										|| expr.charAt(pos) == '.'
										|| expr.charAt(pos) == ','));
						
						/**  convierte número encontrado y  codificado en  string  **/
						try{ 
								Double.valueOf(fragmento);
							}catch(NumberFormatException e){ //Si no pudo pasarlo a número hay un error
							ultimaParseada="0";
							throw new SintaxException("Número mal digitado");
						}
						PilaNumeros.push(new String(fragmento));
						anterior=1;
						pos--;
						
					}else if (expr.charAt(pos)=='x' || expr.charAt(pos)=='e'){ //Si es un número conocido
						if(anterior==1 || anterior==4){//si hay una multiplicación oculta
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}
						PilaNumeros.push(expr.substring(pos,pos+tamano));
						anterior=1;
						
						/* Si es suma, multiplicacion o divison */
						
					}else if (expr.charAt(pos)=='+'
							|| expr.charAt(pos)=='*'
							|| expr.charAt(pos)=='/'
							|| expr.charAt(pos)=='%')
							{						
						/**Hay error si antes de los operadores no hay nada,
						 *  hay un parétesis de apertura o un operador 
						 * */
								if (anterior==0 
										||	anterior==2 
										|| anterior==3)
										throw new SintaxException("Error en la expresion");
						sacaOperadores(PilaNumeros, PilaOperadores, expr.substring(pos,pos+tamano));
						anterior=2;
						//Si es una potencia
					}else if (expr.charAt(pos)=='^'){ 
						if (anterior==0 || anterior==2 || anterior==3) //Hay error si antes de una potencia no hay nada, hay un paréntesis de apertura o un operador
							throw new SintaxException("Error en la expresión");
							
						PilaOperadores.push(new String("^"));
						anterior=2;
					}else if (expr.charAt(pos)=='-'){ //Si es una resta
						if(anterior==0 || anterior==2 || anterior==3){//si hay un menos unario
							PilaNumeros.push(new String("-1"));
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}else{//si el menos es binario
							sacaOperadores(PilaNumeros, PilaOperadores, "-");
						}
						anterior=2;
					}else if (expr.charAt(pos)=='('){
						if (anterior==1 || anterior == 4){ //si hay una multiplicación oculta
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}
						PilaOperadores.push(new String("("));
						anterior=3;
					}else if (expr.charAt(pos)==')'){
						if(anterior!=1 && anterior !=4) //Antes de un cierre de paréntesis sólo puede haber un número u otro cierre de paréntesis, sino hay un error
							throw new SintaxException("Error en la expresión");
						
						while(!PilaOperadores.empty() && parentesis.indexOf(((String)PilaOperadores.peek()))==-1){
							sacaOperador(PilaNumeros, PilaOperadores);
						}
						if(!((String)PilaOperadores.peek()).equals("(")){
							PilaNumeros.push(new String(((String)PilaNumeros.pop()) + " " + ((String)PilaOperadores.pop())));
						}else{
							PilaOperadores.pop();
						}
						anterior=4;
					}
				}else if(tamano>=2){ //Si lo encontrado es de tamano dos o mayor (todas las funciones se manejan igual)
					fragmento=expr.substring(pos,pos+tamano);
					if(fragmento.equals("pi")){ //Si es el número pi
						if(anterior==1 || anterior==4){//si hay una multiplicación oculta
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}
						PilaNumeros.push(fragmento);
						anterior=1;
					}else if(fragmento.equals("rnd()")){ //Si es la función que devuelve un número aleatorio (la única función que se maneja como un número)
						if(anterior==1 || anterior==4){//si hay una multiplicación oculta
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}
						PilaNumeros.push("rnd");
						anterior=1;
					}else{ //Si es cualquier otra función
						if (anterior==1 || anterior == 4){ //si hay una multiplicación oculta
							sacaOperadores(PilaNumeros, PilaOperadores, "*");
						}
						PilaOperadores.push(fragmento.substring(0,fragmento.length()-1)); //Se guarda en la pila de funciones sin el paréntesis de apertura (en postfijo no se necesita)
						anterior=3;
					}
				}
				pos+=tamano;
			}
		
			//Procesa al final
			while(!PilaOperadores.empty()){ //Saca todos los operadores mientras la pila no esté vacía
				if(parentesis.indexOf((String)PilaOperadores.peek())!=-1)
					throw new SintaxException("Hay un paréntesis de más");
				sacaOperador(PilaNumeros, PilaOperadores);
			}
		
		}catch(EmptyStackException e){ //Si en algún momento se intenta sacar de la pila y esté vacía hay un error
			ultimaParseada="0";
			throw new SintaxException("Expresión mal digitada");
		}
		
		ultimaParseada=((String)PilaNumeros.pop()); //Se obtiene el resultado final
		
		if(!PilaNumeros.empty()){ //Si la pila de números no quedó vacía hay un error
			ultimaParseada="0";
			throw new SintaxException("Error en la expresión");
		}
		
		return ultimaParseada; //Se devuelve el resultado evaluado
	}//Parsear
	
	/**
	 *Esta es la función que evalúa una expresión parseada en un valor double.
	 *@param expresionParseada Esta es una expresión en notación postfija (se puede obtener con la función parsear).
	 *@param x El valor double en el que se evaluará la función.
	 *@return El resultado (un valor double) de evaluar x en la expresión.
	 *@exception ArithmeticException Error al evaluar en los reales.
	 */
	public double f(String expresionParseada, double x) throws ArithmeticException{
		Stack<Double> pilaEvaluar = new Stack<Double>(); //Pila de doubles para evaluar
		double a, b; //Estos valores son los que se van sacando de la pila de doubles
		StringTokenizer tokens=new StringTokenizer(expresionParseada); //La expresión partida en tokens
		String tokenActual; //El token que se procesa actualmente
		
		try{
			while(tokens.hasMoreTokens()){ //Haga mientras hayan más tokens
				tokenActual=tokens.nextToken();
				/*
				 *La idea aquí es sacar el token que sigue y verificar que es ese
				 *token y manejarlo de manera:
				 *Si es un número se introduce en la pila de números
				 *Si es una función se sacan el valor o los valores necesarios de la pila
				 *de números y se mete el valor evaluado en la función correspondiente (u 
				 *operador correspondiente).
				 */
				if(tokenActual.equals("e")){ //Si es el número e
					pilaEvaluar.push(new Double(Math.E));
				}else if(tokenActual.equals("pi")){//Si es el número pi
					pilaEvaluar.push(new Double(Math.PI));
				}else if(tokenActual.equals("x")){//Si es una x se introduce el valor a evaluar por el usuario
					pilaEvaluar.push(new Double(x));
				}else if(tokenActual.equals("+")){//Si es una suma se sacan dos números y se suman
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(a+b));
				}else if(tokenActual.equals("-")){//Si es resta se sacan dos valores y se restan (así con todos los operadores)
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(a-b));
				}else if(tokenActual.equals("*")){//Multiplicación
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(a*b));
				}else if(tokenActual.equals("/")){//División
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(a/b));
				}else if(tokenActual.equals("^")){//Potencia
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.pow(a,b)));
				}else if(tokenActual.equals("%")){//Resto de la división entera
					b=((Double)pilaEvaluar.pop()).doubleValue();
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(a%b));
				}else if(tokenActual.equals("ln")){//Si es logaritmo natural sólo se saca un valor de la pila y se evalúa
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log(a)));
				}else if(tokenActual.equals("log")){//Logaritmo en base 10
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log(a)/Math.log(10)));
				}else if(tokenActual.equals("abs")){//Valor absoluto
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.abs(a)));
				}else if(tokenActual.equals("rnd")){//Un número a random simplemente se mete en la pila de números
					pilaEvaluar.push(new Double(Math.random()));
				}else if(tokenActual.equals("sen") || tokenActual.equals("sin")){ //Seno
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.sin(a)));
				}else if(tokenActual.equals("cos")){//Coseno
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.cos(a)));
				}else if(tokenActual.equals("tan")){//Tangente
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.tan(a)));
				}else if(tokenActual.equals("sec")){//Secante
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(1/Math.cos(a)));
				}else if(tokenActual.equals("csc")){//Cosecante
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(1/Math.sin(a)));
				}else if(tokenActual.equals("cot")){//Cotangente
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(1/Math.tan(a)));
				}else if(tokenActual.equals("sgn")){//Signo
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(sgn(a)));
				}else if(tokenActual.equals("asen") || tokenActual.equals("asin")){ //Arcoseno
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.asin(a)));
				}else if(tokenActual.equals("acos")){//Arcocoseno
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.acos(a)));
				}else if(tokenActual.equals("atan")){//Arcotangente
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.atan(a)));
				}else if(tokenActual.equals("asec")){//Arcosecante
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.acos(1/a)));
				}else if(tokenActual.equals("acsc")){//Arcocosecante
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.asin(1/a)));
				}else if(tokenActual.equals("acot")){//Arcocotangente
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.atan(1/a)));
				}else if(tokenActual.equals("senh") || tokenActual.equals("sinh")){//Seno hiperbólico
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double((Math.exp(a)-Math.exp(-a))/2));
				}else if(tokenActual.equals("cosh")){//Coseno hiperbólico
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double((Math.exp(a)+Math.exp(-a))/2));
				}else if(tokenActual.equals("tanh")){//tangente hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double((Math.exp(a)-Math.exp(-a))/(Math.exp(a)+Math.exp(-a))));
				}else if(tokenActual.equals("sech")){//Secante hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(2/(Math.exp(a)+Math.exp(-a))));
				}else if(tokenActual.equals("csch")){//Cosecante hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(2/(Math.exp(a)-Math.exp(-a))));
				}else if(tokenActual.equals("coth")){//Cotangente hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double((Math.exp(a)+Math.exp(-a))/(Math.exp(a)-Math.exp(-a))));
				}else if(tokenActual.equals("asenh") || tokenActual.equals("asinh")){ //Arcoseno hiperbólico
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log(a+Math.sqrt(a*a+1))));
				}else if(tokenActual.equals("acosh")){//Arcocoseno hiperbólico
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log(a+Math.sqrt(a*a-1))));
				}else if(tokenActual.equals("atanh")){//Arcotangente hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log((1+a)/(1-a))/2));
				}else if(tokenActual.equals("asech")){//Arcosecante hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log((Math.sqrt(1-a*a)+1)/a)));
				}else if(tokenActual.equals("acsch")){//Arcocosecante hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log((sgn(a)*Math.sqrt(a*a +1)+1)/a)));
				}else if(tokenActual.equals("acoth")){//Arcocotangente hiperbólica
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.log((a+1)/(a-1))/2));
				}else if(tokenActual.equals("sqrt")){//Raíz cuadrada
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Math.sqrt(a)));
				}else if(tokenActual.equals("round")){//Redondear
					a=((Double)pilaEvaluar.pop()).doubleValue();
					pilaEvaluar.push(new Double(Long.toString(Math.round(a))));
				}else{//si es otra cosa tiene que ser un número, simplemente se mete en la pila
					pilaEvaluar.push(Double.valueOf(tokenActual));
				}
			}//while
		}catch(EmptyStackException e){ //Si en algún momento se acabó la pila hay un error
			throw new ArithmeticException("Expresión mal parseada");
		}catch(NumberFormatException e){ //Si hubo error al traducir un número hay un error
			throw new ArithmeticException("Expresión mal digitada");
		}catch(ArithmeticException e){ //Cualquier otro error de división por cero o logaritmo negativo, etc.
			throw new ArithmeticException("Valor no real en la expresión");
		}
		
		a=((Double)pilaEvaluar.pop()).doubleValue(); //El valor a devolver
		
		if(!pilaEvaluar.empty()) //Si todavía quedo otro valor en la pila hay un error
			throw new ArithmeticException("Expresión mal digitada");
			
		return a;
	}//funcion f
	
	/**
	 *Esta función evalua la última expresión parseada en el valor x.
	 *@param x valor a evaluar.
	 *@return la evaluación del polinomio en el valor x.
	 */
	public double f(double x) throws ArithmeticException{
		try{
			return f(ultimaParseada,x);
		}catch(ArithmeticException e){
			throw e;
		}
	}//Fin de la funcion f
	
	
	
	//FUNCIONES PRIVADAS
	
	
	/*
	 *sacaOperador es una función que se encarga de sacar un operador de la pila
	 *y manejarlo de manera apropiada, ya sea un operador unario o binario
	 */
	private void sacaOperador(Stack<String> Numeros, Stack<String> operadores) throws EmptyStackException{
		String operador, a, b;
		final String operadoresBinarios="+ - * / ^ %"; //Lista de los operadores binarios
		
		try{
			operador=(String)operadores.pop(); //Saca el operador que se debe evaluar
			
			if(operadoresBinarios.indexOf(operador)!=-1){ //Si es un operador binario saca dos elementos de la pila y guarda la operación
				b=(String)Numeros.pop();
				a=(String)Numeros.pop();
				Numeros.push(new String(a+" "+b+" "+operador));
			}else{ //Sino sólo saca un elemento
				a=(String)Numeros.pop();
				Numeros.push(new String(a+" "+operador));
			}
		}catch(EmptyStackException e){
			throw e;
		}
	}//sacaOperador
	
	/**
	 *sacaOperadores saca los operadores que tienen mayor prioridad y mete el nuevo operador
	 *@param PilaNumeros  Pila donde se guardarón los números al parsear
	 *@param PilaOperadores Pila donde se guardarón los operadores al parsear
	 *@param operador operador que se debe evaluar
	 */
	private void sacaOperadores(Stack<String> PilaNumeros, Stack<String> PilaOperadores, String operador)
	{
		//Todas las funciones que se manejan como paréntesis de apertura
		final String parentesis="( ln log abs sen sin cos tan sec csc cot sgn asen asin acos atan asec acsc acot senh sinh cosh tanh sech csch coth sqrt round asenh asinh acosh atanh asech acsch acoth";
		
		//mientras la pila no está vacía, el operador que sigue no sea un paréntesis de apertura, la longitud del operador sea uno (para que sea un operador), y la prioridad indique que tiene que seguir sacando elementos
		while(!PilaOperadores.empty() 
				&& 	parentesis.indexOf((String)PilaOperadores.peek())==-1 
				&& ((String)PilaOperadores.peek()).length()==1 
				&& prioridad(((String)PilaOperadores.peek()).charAt(0))>=prioridad(operador.charAt(0))){
			sacaOperador(PilaNumeros, PilaOperadores); //Saca el siguiente operador
		}
		PilaOperadores.push(operador);//Al final mete el nuevo operador luego de sacar todo lo que tenía que sacar.
	}
	
	/*
	 *Función que devuelve la prioridad de una operacion
	 *indicador operador
	 *-1			Nulo
	 *	0			 +,-
	 *	1			 *, / %
	 *	2			^
	 */
	private int prioridad(char s) {
		if (s=='+' || s=='-') //Si es una suma o una resta devuelve cero
			return 0;
		else if (s=='*' || s=='/' || s=='%') //Si es multiplicación, división o resto de división devuelve uno
			return 1;
		else if (s=='^')//Si es potencia devuelve dos
			return 2;
			
		return -1; //Si no fue nada de lo anterior devuelve -1
	} //Fin de la funcion prioridad

	/**
	 * retorna verdadero si el argumento 
	 * es un número  codifivado en string
	 * 				0<= s <=9?true:false
	 */
	private boolean isNum(String s) {
		if (s.compareTo("0")>=0 && s.compareTo("9")<=0) //Si el caracter está entre 0 y 9 (si es un número)
			return true;
		else
			return false;
	} //Fin de la funcion isNum
	
	/** retorna un string conteniendo 
	 * la expresión sin espacios
	 * @param polinomio expresión expresada en mínisculas
	 */	
	private String quitaEspacios(String polinomio){
		String unspacedString = "";	//Variable donde lee la función

		for(int i = 0; i < polinomio.length(); i++)
			{	//Le quita los espacios a la expresión que leyó
				if(polinomio.charAt(i) != ' ') //Si el caracter no es un espacio lo pone, sino lo quita.
				unspacedString += polinomio.charAt(i);
			}//for
		return unspacedString;
	}//quitaEspacios
	
	/** retorna double representando el signo
	 *@param a digito de la pila de números
	 * 
	 */
	private double sgn(double a){
		if(a<0) //Si el número es negativo devuelve -1
			return -1;
		else if(a>0)//Si es positivo devuelve 1
			return 1;
		else//Si no es negativo ni positivo devuelve cero
			return 0;
	}
	//CLASES PRIVADAS
/**
 * administra el manejo de excepciones cuando ocurre un 
 * error sintáctico en la expresión matemática 
 */
	@SuppressWarnings("serial")
	private class SintaxException extends ArithmeticException{ 
		@SuppressWarnings("unused")
		public SintaxException(){ //Si se llama con el mensaje por defecto
			super("Error de sintaxis en el polinomio"); //El constructor llama a la clase superior
		}
		public SintaxException(String e){ //si se llama con otro mensaje
			super(e); //El constructor llama a la clase superior
		}
	}
}//fin de Parseador