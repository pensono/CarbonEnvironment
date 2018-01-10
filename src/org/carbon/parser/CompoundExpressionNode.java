package org.carbon.parser;

import org.carbon.compiler.CompositeExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

import java.util.HashMap;
import java.util.Map;
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
        Map<String, CarbonExpression> expressionMembers = new HashMap<>();

        for (StatementNode member : members) {
            expressionMembers.put(member.getLabel(), member.getValue().link(scope));
        }

        return new CompositeExpression(scope, expressionMembers);
    }

    @Override
    public String getShortString() {
        return "Compound Expression";
    }

    public Set<StatementNode> getMembers() {
        return members;
    }
}
