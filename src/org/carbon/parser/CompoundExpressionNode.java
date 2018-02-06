package org.carbon.parser;

import org.carbon.compiler.CompoundExpression;
import org.carbon.compiler.TypeException;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.List;

/**
 * @author Ethan
 */
public class CompoundExpressionNode extends ExpressionNode {
    private List<StatementNode> statements;

    public CompoundExpressionNode(List<StatementNode> members) {
        this.statements = members;
    }

    @Override
    public CompoundExpression linkExpression(CarbonScope scope) {
        // Build a dependency graph and linkExpression with a pre-order traversal.
        // Cyclic dependencies are not allowed in Carbon

        // ... eventually

        // For now, lets just require that an item is declared in source before it is referenced.
        // This policy can be relaxed later
        CompoundExpression newExpression = new CompoundExpression(scope);
        for (StatementNode statement : statements) {
            CarbonExpression rhs = statement.getRhs().linkExpression(newExpression);
            if (statement.getLhsType().isPresent()) {
                CarbonInterface lhsInterface = statement.getLhsType().get().linkInterface(scope);
                if (!rhs.getInterface().isSubtypeOf(lhsInterface)) {
                    throw new TypeException(rhs.getShortString() + " is not a subtype of " + lhsInterface.getShortString());
                }
            }

            newExpression.addMember(statement.getLabel(), rhs);
        }

        return newExpression;
    }

    @Override
    public String getShortString() {
        return "Compound Expression";
    }

    public List<StatementNode> getStatements() {
        return statements;
    }
}
