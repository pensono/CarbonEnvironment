package org.carbon.compiler;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.Optional;
import java.util.Stack;

/**
 * An expression that is not compound
 * Created by Ethan Shea on 6/19/2017.
 */
public abstract class PrimeExpression extends CarbonExpression {
    public PrimeExpression(CarbonScope scope, CarbonInterface _interface) {
        super(scope, _interface);
    }

    public Optional<CarbonExpression> getByIdentifier(Stack<String> name) {
        return getScope().getByIdentifier(name);
    }
}
