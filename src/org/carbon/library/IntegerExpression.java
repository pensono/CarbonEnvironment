package org.carbon.library;


import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * An integer that represents exactly one value
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerExpression extends GenericIntegerExpression {
    private int value;

    public IntegerExpression(CarbonExpression parent, int value) {
        super(parent, parent.getMember("Integer").get());
        this.value = value;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        throw new ParseException("Integers can't be parameteritize");
    }

    @Override
    public String getDebugString() {
        return "Integer["+value+"]";
    }
}
