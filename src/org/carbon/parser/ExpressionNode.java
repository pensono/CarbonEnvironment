package org.carbon.parser;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

/**
 * @author Ethan
 */
public abstract class ExpressionNode extends SyntaxNode {
    public abstract CarbonExpression linkExpression(CarbonScope scope);
//    public abstract CarbonExpression linkExpression(CarbonScope scope, CarbonInterface carbonInterface);
}
