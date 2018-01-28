package org.carbon.parser;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

/**
 * @author Ethan
 */
public abstract class ExpressionNode extends SyntaxNode {
    public abstract CarbonExpression link(CarbonScope scope);
//    public abstract CarbonExpression link(CarbonScope scope, CarbonInterface carbonInterface);
}
