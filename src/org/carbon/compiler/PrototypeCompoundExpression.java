package org.carbon.compiler;

import com.google.common.base.*;
import com.google.common.collect.HashBiMap;
import org.carbon.PrettyPrintable;

import java.util.*;
import java.util.Optional;

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
        // Build a dependency graph and link from the top down.
        // Cyclic dependencies are not allowed in Carbon

        // ... eventually

        // For now, lets just require that an item is declared in source before it is referenced.
        // This policy can be relaxed later
            Map<String, CarbonExpression> linkedChildren = new HashMap<>();
            for (Map.Entry<String, PrototypeExpression> child : children.entrySet()) {
                linkedChildren.put(child.getKey(), child.getValue().link(scope)); // This is incorrect. Scope should be this
            }
            return new CompoundExpression(scope, linkedChildren);
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
