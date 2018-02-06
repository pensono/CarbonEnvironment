package org.carbon.compiler;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;
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

    public CompoundExpression(CarbonScope scope){
        this(scope, new HashMap<>());
    }

    public CompoundExpression(CarbonScope scope, Map<String, CarbonExpression> children){
        super(scope, new CarbonInterface(scope));
        this.children = children;
    }

    public CompoundExpression(CarbonScope scope, CarbonInterface carbonInterface, Map<String, CarbonExpression> children){
        super(scope, carbonInterface);
        this.children = children;
    }

    @Override
    public CarbonExpression apply(CarbonExpression parameter) {
        return null;
    }

    @Override
    public String getShortString() {
        return "CompoundExpression : " + getInterface().getShortString();
    }

    public String getBodyString(int level){
        return PrettyPrintable.fullString(children, level + 1);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(children.get(name));
    }

    public void addMember(String name, CarbonExpression member) {
        children.put(name, member);
    }

    public boolean hasMember(String name) {
        return children.containsKey(name);
    }

    public Set<String> getMemberNames() {
        return children.keySet();
    }

    @Override
    public CarbonExpression reduce() {
        Map<String, CarbonExpression> newChildren = new HashMap<>();
        for (String name : children.keySet()){
            newChildren.put(name, children.get(name).reduce());
        }

        return new CompoundExpression(getScope(), getInterface(), newChildren);
    }
}
