grammar EtmsParser;
import EtmsLexer;

program: statment+; //约束一个和多个语法

block:'{' statment* '}' //定于语句表达式中执行的块
    | statment
    ;

statment: varDecl
    | funDecl
    | 'if' '(' expr ')' block ('elseif' '(' expr ')' block)? ('else' block)?
    | 'for' '(' type ID ':' exprList')' block
    | returnExpr
    | expr '=' expr ';' //赋值语句
    | expr ';'
    ;

varDecl: type ID ('=' expr )?';';  //声明的变量

funDecl:type ID '=' etmsFun;  //etms的函数赋值

//etms的函数
etmsFun:'$etms' ('.'ID)+ '('  exprList? ')';

//声明的类型，解析类型
type: 'Number'
    | 'Void'
    | 'List'
    | 'Map'
    | 'String'
    ;

expr: etmsFun   //类似 $etms.f(), $etms.f(x), $etms.f(1,2)的函数调用表达式
    | expr '[' expr ']'  //类似a[] ,a[i][j]的数组索引表达式
    | '-' expr    //一元取反表达式
    | '!' expr   //布尔非表达式
    | expr  ('*' | '/') expr
    | expr ('+' | '-') expr
    | expr OP expr (join (expr OP expr))*   //判断表达式，优先级比较低
    | ID
    | NUMBER
    | STRING
    | '(' expr ')'
    | WS
    ;
exprList:expr (',' expr)*; //参数列表

returnExpr: 'return' expr? ';';  //返回的执行结果

join: AND
    | OR;
