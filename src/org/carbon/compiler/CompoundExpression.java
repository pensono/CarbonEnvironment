package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Ethan
 */
public class CompoundExpression extends CarbonExpression {
    private Map<String, CarbonExpression> children;

    public CompoundExpression(CarbonExpression parent, Map<String, CarbonExpression> children){
        super(parent);
        this.children = children;
    }

    public CompoundExpression(CarbonExpression parent, CarbonExpression supertype, Map<String, CarbonExpression> children){
        super(parent, supertype);
        this.children = children;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        return null;
    }

    @Override
    public String getShortString() {
        return getSupertype().isPresent() ? getSupertype().get().getShortString() + " : " : "" + "CompoundExpression" ;
    }

    public String getPrettyString(int level){
        return getShortString() + "\n" + PrettyPrintable.prettyPrint(children, level + 1);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(children.get(name));
    }

    @Override
    public CarbonExpression reduce() {
        Map<String, CarbonExpression> newChildren = new HashMap<>();
        for (String name : children.keySet()){
            newChildren.put(name, children.get(name).reduce());
        }

        if (getSupertype().isPresent()) {
            return new CompoundExpression(getParent(), getSupertype().get(), newChildren);
        } else {
            return new CompoundExpression(getParent(), newChildren);
        }
    }
}
