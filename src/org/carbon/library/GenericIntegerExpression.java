package org.carbon.library;


import org.carbon.compiler.*;

import java.util.Optional;

/**
 * An integer with no range at all. Must be parameteritized to become useful
 * Created by Ethan Shea on 8/29/2016.
 */
public class GenericIntegerExpression extends CarbonExpression {

    public GenericIntegerExpression(CarbonScope scope) {
        super(scope);
    }

    public GenericIntegerExpression(CarbonScope scope, CarbonExpression supertype){
        super(scope, supertype);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        GenericIntegerExpression integerPrototype = (GenericIntegerExpression) getScope().getByIdentifier("Integer").get();
        switch (name){
            case "<":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x < y, "<", this));
            case ">":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x > y, ">", this));
            case "<=":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x <= y, "<=", this));
            case ">=":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x >= y, ">=", this));
            case "==":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x == y, "==", this));
            case "!=":
                return Optional.of(new ComparisonExpression(getScope(), (x, y) -> x != y, "!=", this));
            case "+":
                return Optional.of(new IntegerArithmeticExpression(getScope(), (x, y) -> x + y, "+", this));
            case "-":
                return Optional.of(new IntegerArithmeticExpression(getScope(), (x, y) -> x - y, "-", this));
            case "*":
                return Optional.of(new IntegerArithmeticExpression(getScope(), (x, y) -> x * y, "*", this));
            case "/":
                return Optional.of(new IntegerArithmeticExpression(getScope(), (x, y) -> x / y, "/", this));
            default:
                return super.getMember(name);
        }
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (parameter instanceof PrototypeIntegerExpression){
            return new IntegerExpression(getScope(), ((PrototypeIntegerExpression)parameter).getValue());
        } else {
            throw new ParseException("Integers can't be parameteritized by:\n" + parameter.getFullString());
        }
    }

    @Override
    public String getShortString() {
        return "Integer";
    }
}
