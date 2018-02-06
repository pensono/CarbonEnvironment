package org.carbon.parser;

import org.carbon.PrettyPrintable;
import org.carbon.compiler.TypeException;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
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
    public CarbonExpression linkExpression(CarbonScope scope) {
        CarbonExpression expression = this.expression.linkExpression(scope);

        if (expression.getInterface().getParameters().size() != arguments.size()) {
            throw new TypeException("Not enough parameters for " + expression.getShortString() +
                    "\nRequired: " + expression.getInterface().getParameters().size() + " Found: " + arguments.size() +
                    "\nParameters:\n"+PrettyPrintable.fullString(arguments, 1));
        }

        List<CarbonInterface> parameterInterfaces = expression.getInterface().getParameters();
        for (int i = 0; i < arguments.size(); i++) {
            ExpressionNode argument = arguments.get(i);
            CarbonInterface parameterInterface = parameterInterfaces.get(i);

            CarbonExpression argumentExpression = argument.linkExpression(scope);
            if (!argumentExpression.getInterface().isSubtypeOf(parameterInterface)){
                throw new TypeException("Interface of argument " + argumentExpression.getShortString() +
                        " is not compatible with " + parameterInterface.getShortString());
            }

            expression = expression.apply(argumentExpression);
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
