package com.sc.ynk.anltr.test;

import com.sc.ynk.anltr.dsl.program.ExprLexer;
import com.sc.ynk.anltr.dsl.program.ExprParser;
import com.sc.ynk.anltr.dsl.program.expression.Program;
import com.sc.ynk.anltr.dsl.program.expression.process.ExpressionProcessor;
import com.sc.ynk.anltr.dsl.program.expression.process.VisitProgram;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * @author Janle on 2022/10/20
 */
public class ProgramTest {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //新建一个词法分析器，处理输入的CharStream
        Lexer arrayInitLexer = new ExprLexer(CharStreams.fromString("i:INT=5\n" +
                "i+23\n" +
                "24*3\n"));
        //新建一个词法符号的缓存区，用于存储词法分析器将生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(arrayInitLexer);
        //新建一个语法分析器，处理词法符号缓存区中的内容
        ExprParser exprParser = new ExprParser(commonTokenStream);
        ParseTree tree = exprParser.prog();
        //创建访问妆化的解析到表达式
        VisitProgram progVisit = new VisitProgram();
        Program program = progVisit.visit(tree);
        if (progVisit.semanticVariables.isEmpty()) {
            ExpressionProcessor ep = new ExpressionProcessor(program.expressions);
            for (String evelValue : ep.getEvalResult()) {
                System.out.println(evelValue);
            }
        } else {
            for (String err : progVisit.semanticVariables) {
                System.out.println(err);
            }
        }
    }
}
