package org.carbon.library;


import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;
import org.carbon.compiler.*;
import org.carbon.parser.ParseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An integer with no range at all. Must be parameteritized to become useful.
 * For the sake of this interpreter, this is a 32-bit two's complement integer
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerExpression extends PrimeExpression {
    private int value;

    public IntegerExpression(CarbonScope scope, int value){
        super(scope, new IntegerInterface(scope));
        this.value = value;
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
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

    private static final Set<String> ops = new HashSet<>(Arrays.asList("<", ">", "<=", ">=", "==", "!=", "+", "-", "*", "/"));
    @Override
    public boolean hasMember(String name) {
        return ops.contains(name);
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
