package org.carbon.library;

import org.carbon.PrettyPrintable;
import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * Created by Ethan Shea on 10/18/2016.
 */
public class IntegerArithmeticExpression extends GenericIntegerExpression {
    private Optional<GenericIntegerExpression> rhs;
    private String operatorName;
    private BiFunction<Integer, Integer, Integer> operator;

    private IntegerArithmeticExpression(GenericIntegerExpression parent, BiFunction<Integer, Integer, Integer> operator, String operatorName, Optional<GenericIntegerExpression> rhs){
        super(parent, parent.getMember("Integer").get());
        this.rhs = rhs;
        this.operatorName = operatorName;
        this.operator = operator;    }

    public IntegerArithmeticExpression(GenericIntegerExpression parent, BiFunction<Integer, Integer, Integer> operator, String operatorName) {
        this(parent, operator, operatorName, Optional.empty());
    }

    public IntegerArithmeticExpression(GenericIntegerExpression parent, BiFunction<Integer, Integer, Integer> operator, String operatorName, GenericIntegerExpression rhs){
        this(parent, operator, operatorName, Optional.of(rhs));
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (rhs.isPresent()){
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization" + this + "\n" + parameter);
        }
        CarbonExpression expression = parameter.link(this);
        if (!expression.isSubtypeOf(getMember("Integer").get())){
            throw new ParseException("Parameter is not a subtype of Integer\n" + parameter.getPrettyString());
        }
        return new IntegerArithmeticExpression((GenericIntegerExpression)getParent(), operator, operatorName, (GenericIntegerExpression) expression);
    }

    public String getPrettyString(int level) {
        return PrettyPrintable.indent(level) + getDebugString() + "\n" +
                getParent().getPrettyString(level + 1) +
                (rhs.isPresent() ? "\n" + rhs.get().getPrettyString(level + 1) : "");
    }

    @Override
    public String getDebugString() {
        return operatorName + ":"+getSupertype().get().getDebugString();
    }

    @Override
    public CarbonExpression reduce(){
        if (!rhs.isPresent()){
            return this;
        }

        // TODO does this always reduce to a GenericIntegerExpression?
        rhs = Optional.of((GenericIntegerExpression) rhs.get().reduce());
        if (getParent() instanceof IntegerExpression && rhs.get() instanceof IntegerExpression){
            int value = operator.apply(((IntegerExpression) getParent()).getValue(),
                    ((IntegerExpression) rhs.get()).getValue());
            return new IntegerExpression(getParent(), value);
        }
        return this;
    }

}
