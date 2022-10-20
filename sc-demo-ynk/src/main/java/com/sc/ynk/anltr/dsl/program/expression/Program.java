package com.sc.ynk.anltr.dsl.program.expression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Janle on 2022/10/20
 */
public class Program {
    public List<Expression> expressions;

    public Program() {
        this.expressions = new ArrayList<>();
    }

    public void add(Expression expression) {
        expressions.add(expression);
    }
}
