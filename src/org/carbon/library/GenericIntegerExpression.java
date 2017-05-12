package org.carbon.library;


import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;
import org.carbon.compiler.PrototypeIntegerExpression;

import java.util.Optional;

/**
 * An integer with no range at all. Must be parameteritized to become useful
 * Created by Ethan Shea on 8/29/2016.
 */
public class GenericIntegerExpression extends CarbonExpression {


    public GenericIntegerExpression(CarbonExpression parent) {
        super(parent);
    }

    public GenericIntegerExpression(CarbonExpression parent, CarbonExpression supertype){
        super(parent, supertype);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        switch (name){
            case "<":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x < y, "<"));
            case ">":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x > y, ">"));
            case "<=":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x <= y, "<="));
            case ">=":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x >= y, ">="));
            case "==":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x == y, "=="));
            case "!=":
                return Optional.of(new ComparisonExpression(this, (x, y) -> x != y, "!="));
            case "+":
                return Optional.of(new IntegerArithmeticExpression(this, (x, y) -> x + y, "+"));
            case "-":
                return Optional.of(new IntegerArithmeticExpression(this, (x, y) -> x - y, "-"));
            case "*":
                return Optional.of(new IntegerArithmeticExpression(this, (x, y) -> x * y, "*"));
            case "/":
                return Optional.of(new IntegerArithmeticExpression(this, (x, y) -> x / y, "/"));
            default:
                return getParent().getMember(name);
        }
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (parameter instanceof PrototypeIntegerExpression){
            return new IntegerExpression(getParent(), ((PrototypeIntegerExpression)parameter).getValue());
        } else {
            throw new ParseException("Integers can't be parameteritized by:\n" + parameter.getFullString());
        }
    }

    @Override
    public String getShortString() {
        return "Integer";
    }
}
