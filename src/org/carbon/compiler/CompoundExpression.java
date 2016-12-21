package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ethan
 */
public class CompoundExpression extends CarbonExpression {
    private HashMap<String, CarbonExpression> children;

    public CompoundExpression(CarbonExpression parent, HashMap<String, CarbonExpression> children){
        super(parent);
        this.children = children;
    }

    public CompoundExpression(CarbonExpression parent, CarbonExpression supertype, HashMap<String, CarbonExpression> children){
        super(parent, supertype);
        this.children = children;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        return null;
    }

    @Override
    public String getShortString() {
        return "CompoundExpression:" + getParent().getShortString();
    }

    public String getPrettyString(int level){
        return getShortString() + "\n" + PrettyPrintable.prettyPrint(children, level + 1);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(children.get(name));
    }
}
