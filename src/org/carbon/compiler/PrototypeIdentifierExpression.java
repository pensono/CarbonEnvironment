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
        // TODO recurse up the scope's parents to find a suitable match
        Optional<CarbonExpression> expr = scope.getByIdentifier(name);
        if (expr.isPresent()){
            return expr.get();
        }
        // Else put this into the scope?
        throw new LinkException("Could not find " + name);
    }
}
