package org.carbon.parser;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;

/**
 * @author Ethan
 */
public abstract class ExpressionNode extends SyntaxNode {
    public abstract CarbonExpression link(CarbonScope scope);
}
