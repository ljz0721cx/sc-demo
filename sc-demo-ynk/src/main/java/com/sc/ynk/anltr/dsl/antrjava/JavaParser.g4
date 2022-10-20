grammar JavaParser;

import JavaLexer;

compilationUnit: importDeclaration
    | classDeclaration
    | methodDeclaration
    ;


classDeclaration
    :   'class' identifier typeParameters? ('extends' typeType)?
        ('implements' typeList)?
        classBody
    ;
importDeclaration
    :   'import' 'static'? qualifiedName ('.' '*')? ';'
    ;
methodDeclaration
    :   typeType identifier formalParameters ('[' ']')* methodDeclarationRest
    |   'void' identifier formalParameters methodDeclarationRest
    ;


methodDeclarationRest
    : qualifiedName (',' qualifiedName)*
    ;

typeParameters
    : '<' typeParameter (',' typeParameter)* '>'
    ;

typeParameter
    : annotation* identifier (EXTENDS annotation* typeBound)?
    ;

typeBound
    : typeType ('&' typeType)*
    ;

typeList
    : typeType (',' typeType)*
    ;

typeType
    : annotation* (classOrInterfaceType | primitiveType) (annotation* '[' ']')*
    ;
classOrInterfaceType
    : identifier typeArguments? ('.' identifier typeArguments?)*
    ;
typeArguments
    : '<' typeArgument (',' typeArgument)* '>'
    ;

typeArgument
    : typeType
    | annotation* '?' ((EXTENDS | SUPER) typeType)?
    ;
primitiveType
    : BOOLEAN
    | CHAR
    | BYTE
    | SHORT
    | INT
    | LONG
    | FLOAT
    | DOUBLE
    ;

qualifiedName
    : identifier ('.' identifier)*
    ;

classBody
    : '{' classBodyDeclaration* '}'
    ;

classBodyDeclaration
    : ';'
    | STATIC? block
    | modifier* memberDeclaration
    ;
block
    : '{' * '}'
    ;

memberDeclaration
    : methodDeclaration
    | classDeclaration
    ;

modifier
    : classOrInterfaceModifier
    | NATIVE
    | SYNCHRONIZED
    | TRANSIENT
    | VOLATILE
    ;
classOrInterfaceModifier
    : annotation
    | PUBLIC
    | PROTECTED
    | PRIVATE
    | STATIC
    | ABSTRACT
    | FINAL    // FINAL for class only -- does not apply to interfaces
    | STRICTFP
    | SEALED // Java17
    | NON_SEALED // Java17
    ;


formalParameters
    : '(' ( receiverParameter?
          | receiverParameter (',' formalParameterList)?
          | formalParameterList?
          ) ')'
    ;

receiverParameter
    : typeType (identifier '.')* THIS
    ;

formalParameterList
    : formalParameter (',' formalParameter)* (',' lastFormalParameter)?
    | lastFormalParameter
    ;

formalParameter
    : variableModifier* typeType variableDeclaratorId
    ;

lastFormalParameter
    : variableModifier* typeType annotation* '...' variableDeclaratorId
    ;

variableModifier
    : FINAL
    | annotation
    ;

variableDeclaratorId
    : identifier ('[' ']')*
    ;

altAnnotationQualifiedName
    : (identifier DOT)* '@' identifier
    ;

annotation
    : ('@' qualifiedName | altAnnotationQualifiedName) ('(' ( elementValuePairs | elementValue )? ')')?
    ;

elementValuePairs
    : elementValuePair (',' elementValuePair)*
    ;

elementValuePair
    : identifier '=' elementValue
    ;

elementValue:annotation
    ;

identifier
    : IDENTIFIER
    | MODULE
    | OPEN
    | REQUIRES
    | EXPORTS
    | OPENS
    | TO
    | USES
    | PROVIDES
    | WITH
    | TRANSITIVE
    | YIELD
    | SEALED
    | PERMITS
    | RECORD
    | VAR
    ;
