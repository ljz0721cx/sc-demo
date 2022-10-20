package com.sc.ynk.anltr.dsl.calc;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义解析
 *
 * @author Janle on 2022/10/3
 */
public class EvalVisitor extends LabeledExprBaseVisitor<Integer> {
    Map<String, Integer> memory = new HashMap<String, Integer>();


    /**
     * ID ’=‘ expr NEWLINE
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitAssign(LabeledExprParser.AssignContext ctx) {
        String id = ctx.ID().getText(); //id在=的左边
        int value = visit(ctx.expr());//计算右侧表达式的值
        memory.put(id, value);//将映射关系存储在计算器的内存中
        return value;
    }

    /**
     * expr NEWLINE
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitPrintExpr(LabeledExprParser.PrintExprContext ctx) {
        Integer value = visit(ctx.expr());//计算expr子节点的值
        System.out.println(value);//打印结果
        return 0;//上边已经打印了结果返回假值就可以
    }


    /**
     * '(' expr ')'  # parens
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitParens(LabeledExprParser.ParensContext ctx) {
        //返回子表达式的值
        return visit(ctx.expr());
    }

    /**
     * expr op=('*' | '/') expr  # MulDiv
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitMulDiv(LabeledExprParser.MulDivContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.MUL) {
            return left * right;
        }
        return left / right;//不是乘法就是除法
    }

    /**
     * expr op=('+' | '-') expr   # AddSub
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitAddSub(LabeledExprParser.AddSubContext ctx) {
        int left = visit(ctx.expr(0));
        int right = visit(ctx.expr(1));
        if (ctx.op.getType() == LabeledExprParser.ADD) {
            return left + right;
        }
        return left - right;//不是加法就是减法
    }

    /**
     * ID
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitId(LabeledExprParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (memory.containsKey(id)) {
            return memory.get(id);
        }
        return 0;
    }

    /**
     * INT
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitInt(LabeledExprParser.IntContext ctx) {
        return Integer.valueOf(ctx.getText());
    }

    /**
     * CLEAR NEWLINE #clear
     *
     * @param ctx
     * @return
     */
    @Override
    public Integer visitClear(LabeledExprParser.ClearContext ctx) {
        memory.clear();
        return super.visitClear(ctx);
    }
}
