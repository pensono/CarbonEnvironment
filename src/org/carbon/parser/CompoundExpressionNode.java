package org.carbon.parser;

import org.carbon.compiler.CompositeExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Ethan
 */
public class CompoundExpressionNode extends ExpressionNode {
    private List<StatementNode> members;

    public CompoundExpressionNode(List<StatementNode> members) {
        this.members = members;
    }

    @Override
    public CompositeExpression link(CarbonScope scope) {
        // Build a dependency graph and link with a pre-order traversal.
        // Cyclic dependencies are not allowed in Carbon

        // ... eventually

        // For now, lets just require that an item is declared in source before it is referenced.
        // This policy can be relaxed later
        CompositeExpression newExpression = new CompositeExpression(scope);
        for (StatementNode member : members) {
            newExpression.addMember(member.getLabel(), member.getValue().link(newExpression));
        }

        return newExpression;
    }

    @Override
    public String getShortString() {
        return "Compound Expression";
    }

    public List<StatementNode> getMembers() {
        return members;
    }
}
