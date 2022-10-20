package com.sc.ynk.aviator;

import com.googlecode.aviator.*;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author Janle on 2022/10/19
 */
public class InterpreterExample {

    public static void main(final String[] args) {
        // 创建解释器
        AviatorEvaluatorInstance engine = AviatorEvaluator.newInstance(EvalMode.INTERPRETER);
        // 打开跟踪执行
        engine.setOption(Options.TRACE_EVAL, true);

        Expression exp = engine.compile("let a = rand(1100);\n" +
                "\n" +
                "if a > 1000 {\n" +
                "  return \"a is greater than 1000.\";\n" +
                "} elsif a > 100 {\n" +
                "  return \"a is greater than 100.\";\n" +
                "} elsif a > 10 {\n" +
                "   return \"a is greater than 10.\";\n" +
                "} else {\n" +
                "   return \"a is less than 10 \";\n" +
                "}\n" +
                "\n" +
                "return \"a is \" + a + \".\";");
        exp.execute();
        //spel
        final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        org.springframework.expression.Expression expression=spelExpressionParser.parseExpression("1+1");
        System.out.println(expression.getValue());

    }
}
