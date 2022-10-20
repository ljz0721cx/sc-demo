package com.sc.ynk.anltr.dsl.antrjava;

import org.antlr.v4.runtime.TokenStream;

/**
 * @author Janle on 2022/10/3
 */
public class ExtractInterfaceListener extends JavaParserBaseListener{
    JavaParserParser parser;

    public ExtractInterfaceListener(JavaParserParser parser) {
        this.parser = parser;
    }

    @Override
    public void enterClassDeclaration(JavaParserParser.ClassDeclarationContext ctx) {
        System.out.println("interface I"+ctx.identifier()+" {");
    }

    @Override
    public void exitClassDeclaration(JavaParserParser.ClassDeclarationContext ctx) {
        System.out.println("}");
    }

    @Override
    public void enterMethodDeclaration(JavaParserParser.MethodDeclarationContext ctx) {
        // need parser to get tokens
        TokenStream tokens = parser.getTokenStream();
        String type = "void";
        if ( ctx.typeType()!=null ) {
            type = tokens.getText(ctx.typeType());
        }
        String args = tokens.getText(ctx.formalParameters());
        System.out.println("\t"+type+" "+ctx.identifier()+args+";");
    }

    @Override
    public void exitMethodDeclaration(JavaParserParser.MethodDeclarationContext ctx) {
        super.exitMethodDeclaration(ctx);
    }
}
