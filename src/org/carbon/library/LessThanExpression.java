package org.carbon.library;

import org.carbon.PrettyPrintable;
import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * @author Ethan
 */
public class LessThanExpression extends BooleanExpression {
    private Optional<GenericIntegerExpression> rhs;

    public LessThanExpression(GenericIntegerExpression parent){
        super(parent, parent.getMember("Boolean").get());
        rhs = Optional.empty();
    }

    public LessThanExpression(GenericIntegerExpression parent, GenericIntegerExpression rhs){
        super(parent, parent.getMember("Boolean").get());
        this.rhs = Optional.of(rhs);
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
        return new LessThanExpression((GenericIntegerExpression)getParent(), (GenericIntegerExpression) expression);
    }

    public String getPrettyString(int level) {
        return PrettyPrintable.indent(level) + getDebugString() + "\n" +
                getParent().getPrettyString(level + 1) +
                (rhs.isPresent() ? "\n" + rhs.get().getPrettyString(level + 1) : "");
    }

    @Override
    public String getDebugString() {
        return "LessThan:"+getSupertype().get().getDebugString();
    }

    @Override
    public CarbonExpression reduce(){
        if (rhs.isPresent() && getParent() instanceof IntegerExpression && rhs.get() instanceof IntegerExpression){
            boolean value = ((IntegerExpression) getParent()).getValue() < ((IntegerExpression) rhs.get()).getValue();
            return new BooleanExpression(getParent(), value);
        }
        return this;
    }
}
