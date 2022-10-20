package com.sc.ynk.anltr.dsl.program.expression.process;

import com.sc.ynk.anltr.dsl.program.ExprBaseVisitor;
import com.sc.ynk.anltr.dsl.program.expression.Program;

import java.util.ArrayList;
import java.util.List;

import static com.sc.ynk.anltr.dsl.program.ExprParser.ProgramContext;

/**
 * @author Janle on 2022/10/20
 */
public class VisitProgram extends ExprBaseVisitor<Program> {
    public List<String> semanticVariables;

    @Override
    public Program visitProgram(ProgramContext ctx) {
        Program program = new Program();
        semanticVariables = new ArrayList<>();
        VisitExpression visiter = new VisitExpression(semanticVariables);
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (i == ctx.getChildCount() - 1) {
                //如果是最后一个EOF遍历结束
            } else {
                //天机遍历的表达式
                program.add(visiter.visit(ctx.getChild(i)));
            }
        }

        return program;
    }
}
