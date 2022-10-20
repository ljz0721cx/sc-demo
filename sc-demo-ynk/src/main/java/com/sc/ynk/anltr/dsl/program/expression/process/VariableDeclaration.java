package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.expression.Expression;

/**
 * @author Janle on 2022/10/20
 */
public class VariableDeclaration extends Expression {
    public String id;
    public String type;
    public int value;

    public VariableDeclaration(String id, String type, int value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }
}
