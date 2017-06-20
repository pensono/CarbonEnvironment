package org.carbon.library;


import org.carbon.CarbonScope;

/**
 * An integer that represents exactly one value
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerExpression extends GenericIntegerExpression {
    private int value;

    public IntegerExpression(CarbonScope scope, int value) {
        super(scope, scope.getByIdentifier("Integer").get());
        this.value = value;
    }

//    @Override
//    public CarbonExpression parameteritize(PrototypeExpression parameter) {
//        throw new ParseException("Integers can't be parameteritize");
//    }

    @Override
    public String getShortString() {
        return "Integer["+value+"]";
    }

    public int getValue() {
        return value;
    }
}
