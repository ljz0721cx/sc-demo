package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.expression.Expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Janle on 2022/10/20
 */
public class ExpressionProcessor {
    private List<Expression> expressions;
    private Map<String, Integer> values;

    public ExpressionProcessor(List<Expression> expressions) {
        this.expressions = expressions;
        values = new HashMap<>();
    }

    /**
     * 解析出来的所有结果
     *
     * @return
     */
    public List<String> getEvalResult() {
        List<String> evaluations = new ArrayList();
        for (Expression expression : expressions) {
            if (expression instanceof VariableDeclaration) {
                VariableDeclaration decl = (VariableDeclaration) expression;
                values.put(decl.id, decl.value);
            } else {
                String input = expression.toString();
                int result = getEvalValue(expression);
                evaluations.add(input + "结果" + result);
            }
        }
        return evaluations;
    }

    /**
     * 获取到值
     *
     * @param e
     * @return
     */
    private int getEvalValue(Expression e) {
        int result = 0;
        if (e instanceof Number) {
            Number num = (Number) e;
            result = num.num;
        } else if (e instanceof Variable) {
            Variable variable = (Variable) e;
            result = values.get(variable.id);
        } else if (e instanceof AddApplication) {
            AddApplication addApplication = (AddApplication) e;
            int left = getEvalValue(addApplication.left);
            int right = getEvalValue(addApplication.right);
            result = left + right;
        } else if (e instanceof Multiplication) {
            Multiplication addApplication = (Multiplication) e;
            int left = getEvalValue(addApplication.left);
            int right = getEvalValue(addApplication.right);
            result = left * right;
        }
        return result;
    }


}
