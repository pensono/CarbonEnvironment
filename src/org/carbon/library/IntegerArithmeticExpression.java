package org.carbon.library;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrimeExpression;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;
import java.util.function.IntBinaryOperator;

/**
 * This can't extend IntegerExpression because it might not be an int. It could be a
 * function which takes an int and returns an int, but not an int itself.
 * Created by Ethan Shea on 10/18/2016.
 */
public class IntegerArithmeticExpression extends PrimeExpression {
    private IntegerExpression lhs;

    // TODO factor a new subclass
    private Optional<IntegerExpression> rhs;
    private String operatorName;
    private IntBinaryOperator operator;

    private IntegerArithmeticExpression(CarbonScope scope, IntegerExpression lhs, Optional<IntegerExpression> rhs, IntBinaryOperator operator, String operatorName) {
        super(scope, new IntegerInterface());
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
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (rhs.isPresent()) {
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization" + this + "\n" + parameter);
        }
        CarbonExpression expression = parameter.link(getScope());
        if (!new IntegerInterface().isSupertypeOf(expression.getInterface())) {
            throw new ParseException("Parameter is not a subtype of Integer. Parameter:\n" + parameter.getFullString());
        }
        return new IntegerArithmeticExpression(getScope(), operator, operatorName, lhs, (IntegerExpression) expression);
    }

    public String getBodyString(int level) {
        return lhs.getBodyString(level + 1) +
                (rhs.isPresent() ? "\n" + rhs.get().getBodyString(level + 1) : "");
    }

    @Override
    public String getShortString() {
        return operatorName + ":" + getInterface().getShortString();
    }

    @Override
    public CarbonExpression reduce() {
        if (!rhs.isPresent()) {
            return this;
        }

        // TODO does this always reduce to a IntegerExpression?
        rhs = Optional.of((IntegerExpression) rhs.get().reduce());
        if (lhs instanceof IntegerExpression && rhs.get() instanceof IntegerExpression) {
            int lhsValue = ((IntegerExpression) lhs).getValue();
            int rhsValue = ((IntegerExpression) rhs.get()).getValue();

            int value = operator.applyAsInt(lhsValue, rhsValue);
            return new IntegerExpression(getScope(), value);
        }
        return this;
    }
}
