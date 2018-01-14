package org.carbon.parser;

import org.carbon.PrettyPrintable;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan
 */
public class AppliedExpressionNode extends ExpressionNode{
    private ExpressionNode expression;
    private List<ExpressionNode> arguments;

    public AppliedExpressionNode(ExpressionNode expression, List<ExpressionNode> arguments) {
        this.expression = expression;
        this.arguments = arguments;
    }

    public AppliedExpressionNode(ExpressionNode expression, ExpressionNode argument) {
        this.expression = expression;
        List<ExpressionNode> arguments = new ArrayList<>();
        arguments.add(argument);
        this.arguments = arguments;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        CarbonExpression expression = this.expression.link(scope);
        for (ExpressionNode argument : arguments) {
            expression = expression.apply(argument.link(scope));
        }
        return expression;
    }

    @Override
    public String getShortString() {
        return "Applied Expression";
    }

    @Override
    public String getBodyString(int level) {
        return PrettyPrintable.indent(level) + "Base: " + expression.getShortString() + "\n" +
                expression.getBodyString(level + 1) + "\n" +
                PrettyPrintable.indent(level) + "Arguments: \n" +
                PrettyPrintable.fullString(arguments, level + 1);
    }
}
