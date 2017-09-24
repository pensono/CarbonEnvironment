package org.carbon.library;


import org.carbon.CarbonScope;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerRangeExpression extends IntegerExpression {
    private int valueLow; //inclusive
    private int valueHigh; // inclusive

    public IntegerRangeExpression(CarbonScope scope, int low, int high) {
        super(scope);
        valueLow = low;
        valueHigh = high;
    }

//    @Override
//    public CarbonExpression parameteritize(PrototypeExpression parameter) {
//        throw new ParseException("Integers can't be parameteritize");
//    }

    @Override
    public String getShortString() {
        return "Integer[" + valueLow + ".." + valueHigh + "]";
    }
}
