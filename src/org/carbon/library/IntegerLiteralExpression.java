package org.carbon.library;


import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.parser.ParseException;

/**
 * An integer with no range at all. Must be parameteritized to become useful.
 * For the sake of this interpreter, this is a 32-bit two's complement integer
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerLiteralExpression extends IntegerExpression {
    private int value;

    public IntegerLiteralExpression(CarbonScope scope, int value){
        super(scope);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public CarbonExpression apply(CarbonExpression expression) {
        throw new ParseException("Integers can't be parameteritized! Attempted:\n" + expression.getFullString());
    }

    @Override
    public String getShortString() {
        return value + " : Integer";
    }
}
