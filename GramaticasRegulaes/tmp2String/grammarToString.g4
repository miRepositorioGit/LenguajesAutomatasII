// Define a grammar called hello
// fuente: Proglang Using ANTLR v4 in Intellij IDE
// https://www.youtube.com/watch?v=eW4WFgRtFeY	
grammar grammarToString;		// startRuleName
start  : 'hello' ID ;           // match keyword start followed by an identifier
ID 	   : [a-z]+ ;               // match lower-case identifiers
WS     : [ \t\r\n]+ -> skip ;   // skip spaces, tabs, newlines