package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.ExprBaseVisitor;
import com.sc.ynk.anltr.dsl.program.ExprParser;
import com.sc.ynk.anltr.dsl.program.expression.Expression;
import org.antlr.v4.runtime.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Janle on 2022/10/20
 */
public class VisitExpression extends ExprBaseVisitor<Expression> {
    private List<String> variables;
    private List<String> semanticVariables;

    public VisitExpression(List<String> semanticVariables) {
        this.variables = new ArrayList<>();
        this.semanticVariables = semanticVariables;
    }

    @Override
    public Expression visitDeclaration(ExprParser.DeclarationContext ctx) {
        Token token = ctx.ID().getSymbol();
        int line = token.getLine();
        int column = token.getCharPositionInLine();
        String id = ctx.getChild(0).getText();
        if (variables.contains(id)) {
            semanticVariables.add("变量" + id + "已经存在");
        } else {
            variables.add(id);
        }
        String type = ctx.getChild(2).getText();
        int value = Integer.valueOf(ctx.NUM().getText());
        return new VariableDeclaration(id, type, value);
    }

    @Override
    public Expression visitMultiplication(ExprParser.MultiplicationContext ctx) {
        Expression left = visit(ctx.getChild(0));
        Expression right = visit(ctx.getChild(2));
        return new Multiplication(left, right);
    }

    @Override
    public Expression visitAddition(ExprParser.AdditionContext ctx) {
        Expression left = visit(ctx.getChild(0));
        Expression right = visit(ctx.getChild(2));
        return new AddApplication(left, right);
    }

    @Override
    public Expression visitVariable(ExprParser.VariableContext ctx) {
        Token token = ctx.ID().getSymbol();
        int line = token.getLine();
        int column = token.getCharPositionInLine();
        String id = ctx.getChild(0).getText();
        if (!variables.contains(id)) {
            semanticVariables.add("变量没有声明");
        }
        return new Variable(id);
    }

    @Override
    public Expression visitNumber(ExprParser.NumberContext ctx) {
        String text = ctx.getChild(0).getText();
        int num = Integer.parseInt(text);
        return new Number(num);
    }
}
