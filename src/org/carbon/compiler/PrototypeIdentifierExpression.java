package org.carbon.compiler;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeIdentifierExpression extends PrototypeExpression {
    private String name;

    public PrototypeIdentifierExpression(String name) {
        this.name = name;
    }

    @Override
    public String getShortString() {
        return "Identifier: " + name;
    }

    public List<String> getDependencies() {
        return Arrays.asList(name);
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        Optional<CarbonExpression> expr = scope.getByIdentifier(name);
        if (expr.isPresent()){
            return expr.get();
        }
        // TODO Better error message showing an example definition
        throw new LinkException("Could not find " + name + ". Did you forget to define it first?");
    }
}
