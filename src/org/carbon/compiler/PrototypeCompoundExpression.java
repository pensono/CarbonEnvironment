package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ethan
 */
public class PrototypeCompoundExpression extends PrototypeExpression {
    private Set<PrototypeExpression> children;

    public PrototypeCompoundExpression(Set<PrototypeExpression> children) {
        this.children = children;
    }


    @Override
    public CarbonExpression link(CarbonExpression scope) {
        Set<CarbonExpression> linkedChildren = new HashSet<>();
        for (PrototypeExpression child : children){
            linkedChildren.add(child.link(expr));
        }
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
