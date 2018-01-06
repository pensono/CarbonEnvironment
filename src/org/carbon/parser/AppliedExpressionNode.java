package org.carbon.parser;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;

import java.util.List;

/**
 * @author Ethan
 */
public class AppliedExpressionNode extends ExpressionNode{
    private IdentifierNode identifier;
    private List<ExpressionNode> arguments;

    public AppliedExpressionNode(IdentifierNode identifier, List<ExpressionNode> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        CarbonExpression expression = identifier.link(scope);
        for (ExpressionNode argument : arguments) {
            expression = expression.parameteritize(argument.link(scope));
        }
        return expression;
    }

    @Override
    public String getShortString() {
        return null;
    }
}
