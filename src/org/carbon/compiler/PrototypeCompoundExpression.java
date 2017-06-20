package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.*;

/**
 * @author Ethan
 */
public class PrototypeCompoundExpression extends PrototypeExpression {
    private Map<String, PrototypeExpression> children;

    public PrototypeCompoundExpression(Map<String, PrototypeExpression> children) {
        this.children = children;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        // Build a dependency graph and link with a pre-order traversal.
        // Cyclic dependencies are not allowed in Carbon

        // ... eventually

        // For now, lets just require that an item is declared in source before it is referenced.
        // This policy can be relaxed later
        CompositeExpression newExpression = new CompositeExpression(scope);
        for (Map.Entry<String, PrototypeExpression> child : children.entrySet()) {
            newExpression.addMember(child.getKey(), child.getValue().link(newExpression)); // This is incorrect. Scope should be this
        }
        return newExpression;
    }

    public String getBodyString(int level) {
        return PrettyPrintable.indent(level) + getShortString() + "\n" +
                PrettyPrintable.prettyPrint(children, level + 1);
    }

    @Override
    public String getShortString() {
        return "Composite Expression";
    }
}
