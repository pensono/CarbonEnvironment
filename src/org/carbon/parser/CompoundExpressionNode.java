package org.carbon.parser;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;

import java.util.Set;

/**
 * @author Ethan
 */
public class CompoundExpressionNode extends ExpressionNode {
    private Set<StatementNode> members;

    public CompoundExpressionNode(Set<StatementNode> members) {
        this.members = members;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        return null;
    }

    @Override
    public String getShortString() {
        return "Compound Expression";
    }

    public Set<StatementNode> getMembers() {
        return members;
    }
}
