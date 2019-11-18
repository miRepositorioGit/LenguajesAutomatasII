/** ensamblador_z80.g4 
 * Interpreta un código fuente codificado en lenguaje ensamblador para un procesador Z80.
 *  identifica un:  startRuleName/simboloIncial, linea y una sentencia para imprimir valor númerico entero 
 * en pantalla.
 * 
 * starRuleName/simboloInicial senala al analizador léxico el comienzo de identificación TOKENS y 
 * genera como salida un flujo de TOKESN (token Stream).
 * Una linea - apartir de ahora (line), es una estructura gramátical libre de contexto
 * compuesta por  sentence. 
 * sentence es una gramática de atributos estructurada, permite combinar atributos, 
 * atributos-código en JAVA útil para el analizador sitáctico -interpretación de código. 
 * La gramática de atributos  esta compuesta por TOKENS no-terminales, terminales y produciones [1].  
 * La producción presentada por sentence está compuesta  por una colección de elementos 
 * no-terminales { reg_assign 	  | add_operation 	  | print 	  | comment }   que combinan atributos 
 * (.text,.value) y abastecen a la tabla de símbolos (symbolTable)   
 *    
 * Según [3,4] una linea de código ensamblador para procesador Z80, se encuentra compuesta por: 
 * label instruction | directive comment
 * 		|		|					|				|---   comentario de línea.  
 *      |		|					|---------------- 	pseudo operación que complementa la acción del código ensamblador.
 * 	    |		|-------------------------------  opcode expresionlist
 * 		|															|			| --------	REGISTRO, [REGISTRO] [NUMBER]
 * 		|															|------------------  	operación especializada
 * 		|------------------------------------- etiqueta para identificar segmentos de código.
 * 
 * Para la producción, reg_assign está compuesta por:
 * 					 LD_OPCODE REGISTER COMMA  argument 
 * 							|					|				|				|------ expresión: Número entero sin signo.
 * 							|					|				|------------------ separador de argumento.	
 *                         |                    |------------------------------ Registro interno procesador Z80 {a,b,..., i,r} en minúscula.
 * 							|--------------------------------------------- Código para abastecer registros mediante direccionamiento inmediato.
 * expresión es un elemento no-terminal compuesto por uno o más elemento terminal (identificador)
 * donde se puede insertar código JAVA [1], retorna un atributo value del tipo Object. 
 * En cada asignación  de value  a cada identificador es necesario:
 * 							 $value = $NUMBER.text 
 * 									|				|			|------ lexema, atributo tipo texto,  específico para un token
 * 									|               |--------------- token NUMERO, representa [0-9]
 * 								    |--------------------------- atributo tipo Object
 * 	$ es un identificador para atributos-tokens ANTLR-V4.
 * 						
 * Por ejemplo, para una producción, reg_assign:  
 *  								LD a,5 ; Carga un número entero sin signo al registro a.
 * 
 * La producción add_operation está compuesta por:
 * 					 ADD_OPCODE sumar*
 * 						|			|			|----------- expresión
 *   					|			|-------------------- Código para ejecutar una suma entre dos números enteros.
 * Por ejemplo : 
 * 						ADD a,b ; Suma aritmética entre dos registros
 * 
 * 										NO SOPORTA COMENTARIOS en código fuente.
 * fuente:
 * [1] Pavlich-Mariscal J. Tutorial ANTLRv4 Universidad Pontificia de Chile. https://www.youtube.com/playlist?list=PL5BoUl9EDVnBojdOv9J9S9KZPJdOc6HTw
 * [2] Antonio-Lorenzana F.	Lenguajes y automatas II, ITTlahuac.(13 Nov 2019).   
 * [3]  R.  Zaks Programming the Z80. USA: SYBEX 1981 ISBN: 0-89588-094-6
 * [4]  antlr/grammars-v4 https://github.com/antlr/grammars-v4/blob/master/asmZ80/asmZ80.g4
 * */

grammar ensamblador_z80;
@parser::header{
	
	import java.util.Map;
	import java.util.HashMap;
}
@parser::members{
	
	Map<String, Object> symbolTable= new HashMap<String, Object>();
}
 
program :	PROGRAM line ;

line : sentence* ;

sentence:	  reg_assign 	  | add_operation 	  | print 	  | comment ;

reg_assign: 	LD_OPCODE REGISTER COMMA  argument 
	{
		symbolTable.put($REGISTER.text,$argument.value);
	} ;

add_operation : ADD_OPCODE sumar*
{
	symbolTable.put("a",$sumar.value);
} ;
 
print: PRN argument 
	{
		System.out.println($argument.value);
	};
	
comment:	COMMENT ;

argument returns [Object value]:
	 REGISTER     	{$value = symbolTable.get($REGISTER.text); }
	| NUMBER 	    {$value = Integer.parseInt($NUMBER.text); }
	 ;

sumar returns [Object value]:
						t1 = argument 	{ $value = (int)$t1.value; }
		( COMMA t2 = argument { $value = (int)$value + (int)$t2.value;} 	)*;

PROGRAM: 'program';
LD_OPCODE:	'LD' ;
ADD_OPCODE: 'ADD';
PRN: 'prn';
REGISTER:	 [a-z];
COMMA :',';
NUMBER: [0-9];
ID: 	[a-zA-Z] [a-zA-Z0-9_]* ;
COMMENT : 	';' ~[a-zA-Z]+[\r\n]* -> skip ;
WS :[  \t\r\n]+ -> skip;
