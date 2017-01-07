package org.carbon.compiler;

import com.google.common.collect.HashBiMap;
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
    public CarbonExpression link(CarbonExpression scope) {
        return new LazyLinkExpression((CarbonExpression parent) -> {
            Map<String, CarbonExpression> linkedChildren = new HashMap<>();
            for (Map.Entry<String, PrototypeExpression> child : children.entrySet()){
                linkedChildren.put(child.getKey(), child.getValue().link(parent));
            }
            return new CompoundExpression(parent, linkedChildren);
        },scope);
    }

    public String getPrettyString(int level) {
        return PrettyPrintable.indent(level) + getShortString() + "\n" +
                PrettyPrintable.prettyPrint(children, level + 1);
    }

    @Override
    public String getShortString() {
        return "Composite Expression";
    }
}
