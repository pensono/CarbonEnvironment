package org.carbon.library;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * @author Ethan
 */
public class BooleanExpression extends CarbonExpression {
    public BooleanExpression(CarbonExpression parent) {
        super(parent);
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameterList) {
        throw new ParseException("Booleans can't be parameteritized");
    }

    @Override
    public String getDebugString() {
        return "Boolean";
    }
}
