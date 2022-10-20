package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.expression.Expression;

/**
 * @author Janle on 2022/10/20
 */
public class Variable extends Expression {
    public String id;
    public Variable(String id){
        this.id=id;
    }

    @Override
    public String toString() {
        return id;
    }
}

