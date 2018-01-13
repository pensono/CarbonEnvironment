package org.carbon.parser;

import org.carbon.PrettyPrintable;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

/**
 * @author Ethan
 */
public class MemberNode extends ExpressionNode {
    private IdentifierNode identifier;
    private ExpressionNode base;

    public MemberNode(ExpressionNode base, IdentifierNode identifier) {
        this.base = base;
        this.identifier = identifier;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public ExpressionNode getBase() {
        return base;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        CarbonExpression baseExpression = base.link(scope);
        return identifier.link(baseExpression);
    }

    @Override
    public String getShortString() {
        return "Member Node: " + identifier.getShortString();
    }

    @Override
    public String getBodyString(int level){
        return identifier.getBodyString(level) + base.getBodyString(level);
    }
}
