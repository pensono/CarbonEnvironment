package org.carbon.library;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.compiler.*;

import java.util.Optional;

/**
 * @author Ethan
 */
public class BooleanExpression extends PrimeExpression {
    /**
     * Empty means the bool could be either true or false.
     * Populated means only true or false
     */
    private Optional<Boolean> value;

    public BooleanExpression(CarbonScope scope) {
        super(scope, scope.getByIdentifier("Boolean"));
        value = Optional.empty();
    }

    public BooleanExpression(CarbonScope scope, boolean value) {
        super(scope, scope.getByIdentifier("Boolean").get());
        this.value = Optional.of(value);
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameterList) {
        throw new ParseException("Booleans can't be parameteritized");
    }

    @Override
    public String getShortString() {
        return value.isPresent() ? "Boolean[" + value.get() + "]" : "Boolean";
    }

    public Optional<Boolean> getValue() {
        return value;
    }
}
