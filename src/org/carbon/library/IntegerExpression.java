package org.carbon.library;

import org.carbon.compiler.PrimeExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

import java.util.Optional;

/**
 * @author Ethan
 */
public abstract class IntegerExpression extends PrimeExpression  {
    public IntegerExpression(CarbonScope scope) {
        super(scope, new IntegerInterface(scope));
    }

    public IntegerExpression(CarbonScope scope, IntegerInterface _interface) {
        super(scope, _interface);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        switch (name){
            case "<":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x < y, "<", this));
            case ">":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x > y, ">", this));
            case "<=":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x <= y, "<=", this));
            case ">=":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x >= y, ">=", this));
            case "==":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x == y, "==", this));
            case "!=":
                return Optional.of(new IntegerComparisonExpression(getScope(), (x, y) -> x != y, "!=", this));
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
}
