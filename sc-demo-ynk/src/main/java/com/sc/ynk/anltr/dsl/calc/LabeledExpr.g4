grammar LabeledExpr;

prog: stat+;

stat : expr NEWLINE  # printExpr
    | ID '=' expr NEWLINE   # assign
    | CLEAR NEWLINE #clear
    | NEWLINE    # black
    ;

expr : expr op=('*' | '/') expr  # MulDiv
    | expr op=('+' | '-') expr   # AddSub
    | INT   # int
    | ID   # id
    | '(' expr ')'  # parens
    ;


CLEAR : ';' ;
ID : [a-zA-Z]+;
INT: [0-9]+;
NEWLINE:'\r'?'\n';
WS: [ \t]+ ->skip;


MUL: '*';
DIV: '/';
ADD: '+';
SUB: '-';
