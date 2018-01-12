package org.carbon.library;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.compiler.*;
import org.carbon.parser.ParseException;

import java.util.Optional;

/**
 * @author Ethan
 */
public class BooleanExpression extends PrimeExpression {
    /**
     * Empty means the bool could be either true or false.
     * Populated means only true or false
     */
    private boolean value;

    public BooleanExpression(CarbonScope scope, boolean value) {
        super(scope, new BooleanInterface(scope));
        this.value = value;
    }

    @Override
    public CarbonExpression apply(CarbonExpression expression) {
        throw new ParseException("Booleans can't be parameteritized");
    }

    @Override
    public String getShortString() {
        return Boolean.toString(value) + " : Boolean";
    }

    public boolean getValue() {
        return value;
    }
}
