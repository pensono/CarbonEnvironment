package org.carbon.library;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * @author Ethan
 */
public class BooleanExpression extends CarbonExpression {
    /**
     * Empty means the bool could be either true or false.
     * Populated means only true or false
     */
    Optional<Boolean> value;

    public BooleanExpression(CarbonExpression parent) {
        super(parent);
        value = Optional.empty();
    }

    public BooleanExpression(CarbonExpression parent, CarbonExpression supertype) {
        super(parent, supertype);
        value = Optional.empty();
    }

    public BooleanExpression(CarbonExpression parent, boolean value) {
        super(parent);
        this.value = Optional.of(value);
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameterList) {
        throw new ParseException("Booleans can't be parameteritized");
    }

    @Override
    public String getShortString() {
        return value.isPresent() ? "Boolean{" + value.get() + "]" : "Boolean";
    }
}
