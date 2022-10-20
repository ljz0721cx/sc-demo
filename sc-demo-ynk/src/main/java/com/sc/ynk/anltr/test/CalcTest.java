package com.sc.ynk.anltr.test;

import com.sc.ynk.anltr.dsl.calc.EvalVisitor;
import com.sc.ynk.anltr.dsl.calc.LabeledExprLexer;
import com.sc.ynk.anltr.dsl.calc.LabeledExprParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * 输入
 * * ab=3
 * * bc=4
 * * ab*bc
 * * ;
 * * ab
 * * bc
 * <p>
 * 执行结果
 * 12
 * 0
 * 0
 *
 * @author Janle on 2022/10/3
 */
public class CalcTest {
    public static void main(String[] args) throws IOException {
        //新建一个标准的输入流
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(System.in);
        //新建一个词法分析器，处理输入的CharStream
        Lexer arrayInitLexer = new LabeledExprLexer(antlrInputStream);
        //新建一个词法符号的缓存区，用于存储词法分析器将生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(arrayInitLexer);
        LabeledExprParser labeledExprParser = new LabeledExprParser(commonTokenStream);

        ParseTree tree = labeledExprParser.prog();

        EvalVisitor evalVisitor = new EvalVisitor();

        evalVisitor.visit(tree);

    }
}

