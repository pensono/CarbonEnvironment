package org.carbon.parser;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

/**
 * @author Ethan
 */
public class OperatorNode extends ExpressionNode {
    private ExpressionNode base;
    private String operator;
    private ExpressionNode argument;

    public OperatorNode(ExpressionNode base, String operator, ExpressionNode argument) {
        this.base = base;
        this.operator = operator;
        this.argument = argument;
    }

    public ExpressionNode getBase() {
        return base;
    }

    public String getOperator() {
        return operator;
    }

    public ExpressionNode getArgument() {
        return argument;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        CarbonExpression baseExpr = base.link(scope);

        CarbonExpression resolvedExpr = baseExpr.getMember(operator)
                .orElseThrow(() -> new ParseException("Cannot find " + operator + " in " + baseExpr.getShortString()));

        return resolvedExpr.apply(argument.link(scope));
    }

    @Override
    public String getShortString() {
        return null;
    }
}
