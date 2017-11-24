package org.carbon.compiler;

import org.carbon.CarbonExpression;
import org.carbon.CarbonInterface;
import org.carbon.CarbonScope;

import java.util.Optional;

/**
 * An expression that is not compound
 * Created by Ethan Shea on 6/19/2017.
 */
public abstract class PrimeExpression extends CarbonExpression {
    public PrimeExpression(CarbonScope scope) {
        super(scope);
    }

    public PrimeExpression(CarbonScope scope, CarbonInterface supertype) {
        super(scope, supertype);
    }

    public Optional<CarbonExpression> getByIdentifier(String name) {
        return getScope().getByIdentifier(name);
    }
}
