package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.expression.Expression;

/**
 * 数字表达式
 * @author Janle on 2022/10/20
 */
public class Number extends Expression {
    int num;

    public Number(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return new Integer(num).toString();
    }
}
