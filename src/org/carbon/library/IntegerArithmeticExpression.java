package org.carbon.library;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.CarbonScope;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;
import java.util.function.IntBinaryOperator;

/**
 * Created by Ethan Shea on 10/18/2016.
 */
public class IntegerArithmeticExpression extends GenericIntegerExpression {
    private GenericIntegerExpression lhs;

    // TODO factor a new subclass
    private Optional<GenericIntegerExpression> rhs;
    private String operatorName;
    private IntBinaryOperator operator;

    private IntegerArithmeticExpression(CarbonScope scope, GenericIntegerExpression lhs, Optional<GenericIntegerExpression> rhs, IntBinaryOperator operator, String operatorName) {
        super(scope, scope.getByIdentifier("Integer").get());
        this.rhs = rhs;
        this.operatorName = operatorName;
        this.operator = operator;
    }

    public IntegerArithmeticExpression(CarbonScope scope, IntBinaryOperator operator, String operatorName, GenericIntegerExpression lhs) {
        this(scope, lhs, Optional.empty(), operator, operatorName);
    }

    public IntegerArithmeticExpression(CarbonScope scope, IntBinaryOperator operator, String operatorName, GenericIntegerExpression lhs, GenericIntegerExpression rhs) {
        this(scope, lhs, Optional.of(rhs), operator, operatorName);
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (rhs.isPresent()) {
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization" + this + "\n" + parameter);
        }
        CarbonExpression expression = parameter.link(getScope());
        if (!expression.isSubtypeOf(getMember("Integer").get())) {
            throw new ParseException("Parameter is not a subtype of Integer. Parameter:\n" + parameter.getFullString());
        }
        return new IntegerArithmeticExpression(getScope(), operator, operatorName, lhs, (GenericIntegerExpression) expression);
    }

    public String getBodyString(int level) {
        return lhs.getBodyString(level + 1) +
                (rhs.isPresent() ? "\n" + rhs.get().getBodyString(level + 1) : "");
    }

    @Override
    public String getShortString() {
        return operatorName + ":" + getSupertype().get().getShortString();
    }

    @Override
    public CarbonExpression reduce() {
        if (!rhs.isPresent()) {
            return this;
        }

        // TODO does this always reduce to a GenericIntegerExpression?
        rhs = Optional.of((GenericIntegerExpression) rhs.get().reduce());
        if (lhs instanceof IntegerExpression && rhs.get() instanceof IntegerExpression) {
            int lhsValue = ((IntegerExpression) lhs).getValue();
            int rhsValue = ((IntegerExpression) rhs.get()).getValue();

            int value = operator.applyAsInt(lhsValue, rhsValue);
            return new IntegerExpression(getScope(), value);
        }
        return this;
    }
}
