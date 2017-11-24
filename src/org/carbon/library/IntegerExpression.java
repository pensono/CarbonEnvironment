package org.carbon.library;


import org.carbon.CarbonExpression;
import org.carbon.CarbonInterface;
import org.carbon.CarbonScope;
import org.carbon.compiler.*;

import java.util.Optional;

/**
 * An integer with no range at all. Must be parameteritized to become useful.
 * For the sake of this interpreter, this is a 32-bit two's complement integer
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerExpression extends PrimeExpression {
    private int value;

    public IntegerExpression(CarbonScope scope, int value){
        super(scope, new IntegerInterface());
        this.value = value;
    }

    public IntegerExpression(CarbonScope scope, CarbonInterface carbonInterface, int value){
        super(scope, carbonInterface);
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

    public int getValue() {
        return value;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        throw new ParseException("Integers can't be parameteritized! Attempted:\n" + parameter.getFullString());
    }

    @Override
    public String getShortString() {
        return "Integer";
    }
}
