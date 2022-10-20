lexer grammar EtmsLexer;

ID:[a-zA-Z]+;

OP : '>'
   | '<'
   | '=='
   | '!='
   | '>='
   | '<='
   | 'in'
   | 'not in'
   | 'not  in'
   | 'like'
   ;

AND : '&&' ; //代码中的处理
OR : '||' ; //代码中的或

NUMBER : '-'? DIGIT '.' DIGIT EXP?   //1.35， 1.35E-9 ,0.3 -4.5
        | '-'? DIGIT EXP   //1e10， -3e4
        | '-'? DIGIT     //-3，88
              ;
STRING: '"' ('\\"' | .)*? '"'; //字符串

fragment INT: '0' | [1-9] [0-9]*;  //整数，除了0可以独立，其他的不能0开头
fragment EXP: [Ee] [+\-]? INT;
fragment DIGIT : [0-9] ;
fragment EXPONET : ('e'|'E') ('+'|'-')? DIGIT+ ;
COMMENT: '/*' .*? '*/' ->skip;


WS
:
	[ \t\r\n]+ -> channel(HIDDEN)
;

NL
:
    '\r'? '\n' ->channel(HIDDEN)
;
