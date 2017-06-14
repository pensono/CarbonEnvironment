package org.carbon.library;


import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.CarbonScope;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerRangeExpression extends GenericIntegerExpression {
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
