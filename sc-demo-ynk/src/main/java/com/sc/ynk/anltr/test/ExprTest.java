package com.sc.ynk.anltr.test;

import com.sc.ynk.anltr.dsl.expr.ExprLexer;
import com.sc.ynk.anltr.dsl.expr.ExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Janle on 2022/9/30
 */
public class ExprTest {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String inputFile = null;
        if (args.length > 0) {
            inputFile = args[0];
        }
        InputStream is = System.in;
        if (inputFile != null) {
            is = new FileInputStream(inputFile);
        }
        //新建一个标准的输入流
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(is);
        //新建一个词法分析器，处理输入的CharStream
        Lexer arrayInitLexer = new ExprLexer(antlrInputStream);
        //新建一个词法符号的缓存区，用于存储词法分析器将生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(arrayInitLexer);
        //新建一个语法分析器，处理词法符号缓存区中的内容
        ExprParser exprParser = new ExprParser(commonTokenStream);
        ParseTree tree = exprParser.prog();
        System.out.println(tree.toStringTree(exprParser));

    }
}
