package org.carbon.compiler;

import org.carbon.library.IntegerExpression;
import org.carbon.library.IntegerRangeExpression;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeIntegerExpression extends PrototypeExpression {
    private int value;

    public PrototypeIntegerExpression(int value) {
        this.value = value;
    }

    @Override
    public String getShortString() {
        return "Integer(" + Integer.toString(value) + ")";
    }

    @Override
    public CarbonExpression link(CarbonExpression scope) {
        return new IntegerExpression(scope, value);
    }

    public int getValue() {
        return value;
    }
}
