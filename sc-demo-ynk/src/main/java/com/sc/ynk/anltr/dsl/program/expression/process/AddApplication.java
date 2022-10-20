package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.expression.Expression;

/**
 * @author Janle on 2022/10/20
 */
public class AddApplication extends Expression {
    Expression left;
    Expression right;

    public AddApplication(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return left.toString() + "+" + right.toString();
    }
}
