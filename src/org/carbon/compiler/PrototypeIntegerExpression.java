package org.carbon.compiler;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.library.SpecificIntegerExpression;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getDependencies() {
        return new ArrayList<>();
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        return new SpecificIntegerExpression(scope, value);
    }

    public int getValue() {
        return value;
    }
}
