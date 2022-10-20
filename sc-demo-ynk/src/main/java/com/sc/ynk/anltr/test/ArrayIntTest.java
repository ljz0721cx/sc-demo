package com.sc.ynk.anltr.test;

import com.sc.ynk.anltr.dsl.arrayint.ArrayIntLexer;
import com.sc.ynk.anltr.dsl.arrayint.ArrayIntParser;
import com.sc.ynk.anltr.dsl.arrayint.ShortToUnicodeString;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

/**
 * @author Janle on 2022/9/30
 */
public class ArrayIntTest {
    public static void main(String[] args) throws IOException {
        //新建一个标准的输入流
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(System.in);
        //新建一个词法分析器，处理输入的CharStream
        Lexer arrayInitLexer = new ArrayIntLexer(antlrInputStream);
        //新建一个词法符号的缓存区，用于存储词法分析器将生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(arrayInitLexer);
        //新建一个语法分析器，处理词法符号缓存区中的内容
        ArrayIntParser arrayIntParser = new ArrayIntParser(commonTokenStream);
        //针对规则进行语法分析初始化
        ParseTree tree = arrayIntParser.init();
        //使用lisp风格打印生成的树
        System.out.println(tree.toStringTree(arrayIntParser));


        //新建一个通用的，能够触发回调函数的语法分析树遍历器
        ParseTreeWalker walker = new ParseTreeWalker();
        //遍历语法过程中生成的语法分析树，触发添加ShortToUnicodeString回调函数
        walker.walk(new ShortToUnicodeString(),tree);
        System.out.println();
    }
}
