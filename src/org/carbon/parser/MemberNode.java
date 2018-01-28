package org.carbon.parser;

import org.carbon.compiler.LinkException;
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

    @Override
    public CarbonExpression link(CarbonScope scope) {
        CarbonExpression baseExpression = base.link(scope);

        for (String name : identifier) {
            baseExpression = baseExpression.getMember(name)
                .orElseThrow(() -> new LinkException("Could not find member " + name + " in " + base.getShortString()));
        }
        return baseExpression;
    }

    @Override
    public String getShortString() {
        return "Member Node: " + identifier.getShortString();
    }

    @Override
    public String getBodyString(int level){
        return base.getFullString(level);
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public ExpressionNode getBase() {
        return base;
    }
}
