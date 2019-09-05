grammar Calc;

start : expr EOF;		// startRuleName

expr  : expr op expr # binaryOp
        | Num        # num
        ;

op    : '+'         
      | '-'  
      | '/'  
      | '*'  
      ;

Num: [0-9]+;