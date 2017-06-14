package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ethan
 */
public class CompoundExpression extends CarbonExpression {
    private Map<String, CarbonExpression> children;

    public CompoundExpression(CarbonScope scope, Map<String, CarbonExpression> children){
        super(scope);
        this.children = children;
    }

    public CompoundExpression(CarbonScope scope, CarbonExpression supertype, Map<String, CarbonExpression> children){
        super(scope, supertype);
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

    public String getBodyString(int level){
        return PrettyPrintable.prettyPrint(children, level + 1);
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
            return new CompoundExpression(getScope(), getSupertype().get(), newChildren);
        } else {
            return new CompoundExpression(getScope(), newChildren);
        }
    }
}
