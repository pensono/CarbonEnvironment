package org.carbon.library;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.parser.ParseException;

import java.util.Optional;
import java.util.function.IntBinaryOperator;

/**
 * This can't extend IntegerLiteralExpression because it might not be an int. It could be a
 * function which takes an int and returns an int, but not an int itself. <-- on second thought may not be true
 * Created by Ethan Shea on 10/18/2016.
 */
public class IntegerArithmeticExpression extends IntegerExpression {
    private IntegerExpression lhs;

    // TODO factor a new subclass
    private Optional<IntegerExpression> rhs;
    private String operatorName;
    private IntBinaryOperator operator;

    private IntegerArithmeticExpression(CarbonScope scope, IntegerExpression lhs, Optional<IntegerExpression> rhs, IntBinaryOperator operator, String operatorName) {
        super(scope);
        this.lhs = lhs;
        this.rhs = rhs;
        this.operatorName = operatorName;
        this.operator = operator;
    }

    public IntegerArithmeticExpression(CarbonScope scope, IntBinaryOperator operator, String operatorName, IntegerExpression lhs) {
        this(scope, lhs, Optional.empty(), operator, operatorName);
    }

    public IntegerArithmeticExpression(CarbonScope scope, IntBinaryOperator operator, String operatorName, IntegerExpression lhs, IntegerExpression rhs) {
        this(scope, lhs, Optional.of(rhs), operator, operatorName);
    }

    @Override
    public CarbonExpression apply(CarbonExpression expression) {
        if (rhs.isPresent()) {
            //double application, what happens now?
            throw new ParseException("Double parametrization" + getShortString() + "\n" + expression);
        }
        if (!IntegerInterface.isSupertypeOfUnparameterized(expression.getInterface())) {
            throw new ParseException("Parameter is not a subtype of Integer. Parameter:\n" + expression.getFullString());
        }
        return new IntegerArithmeticExpression(getScope(), operator, operatorName, lhs, (IntegerExpression) expression);
    }

    public String getBodyString(int level) {
        return lhs.getFullString(level + 1) +
                (rhs.isPresent() ? "\n" + rhs.get().getFullString(level + 1) : "");
    }

    @Override
    public String getShortString() {
        return operatorName + " : " + getInterface().getShortString();
    }

    @Override
    public CarbonExpression reduce() {
        if (!rhs.isPresent()) {
            return this;
        }

        // TODO does this always reduce to a IntegerLiteralExpression?
        // TODO keep immutibility here
        rhs = Optional.of((IntegerLiteralExpression) rhs.get().reduce());
        lhs = (IntegerLiteralExpression) lhs.reduce();
        if (IntegerInterface.isSupertypeOfUnparameterized(lhs.getInterface()) &&
                IntegerInterface.isSupertypeOfUnparameterized(rhs.get().getInterface()) &&
                rhs.get() instanceof IntegerLiteralExpression &&
                lhs instanceof IntegerLiteralExpression) {
            int lhsValue = ((IntegerLiteralExpression)lhs).getValue();
            int rhsValue = ((IntegerLiteralExpression)rhs.get()).getValue();

            int value = operator.applyAsInt(lhsValue, rhsValue);
            return new IntegerLiteralExpression(getScope(), value);
        }
        return this;
    }
}
