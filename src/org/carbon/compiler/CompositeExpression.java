package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Ethan
 */
public class CompositeExpression extends CarbonExpression {
    private Map<String, CarbonExpression> children;

    public CompositeExpression(CarbonScope scope){
        this(scope, new HashMap<>());
    }

    public CompositeExpression(CarbonScope scope, Map<String, CarbonExpression> children){
        super(scope);
        this.children = children;
    }

    public CompositeExpression(CarbonScope scope, CarbonExpression supertype, Map<String, CarbonExpression> children){
        super(scope, supertype);
        this.children = children;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        return null;
    }

    @Override
    public String getShortString() {
        return getSupertype().isPresent() ? getSupertype().get().getShortString() + " : " : "" + "CompositeExpression" ;
    }

    public String getBodyString(int level){
        return PrettyPrintable.prettyPrint(children, level + 1);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(children.get(name));
    }

    public void addMember(String name, CarbonExpression member) {
        children.put(name, member);
    }

    @Override
    public CarbonExpression reduce() {
        Map<String, CarbonExpression> newChildren = new HashMap<>();
        for (String name : children.keySet()){
            newChildren.put(name, children.get(name).reduce());
        }

        if (getSupertype().isPresent()) {
            return new CompositeExpression(getScope(), getSupertype().get(), newChildren);
        } else {
            return new CompositeExpression(getScope(), newChildren);
        }
    }

    @Override
    public Optional<CarbonExpression> getByIdentifier(String name) {
        if (children.containsKey(name)) {
            return Optional.of(children.get(name));
        } else {
            return getScope().getByIdentifier(name);
        }
    }
}
