package org.carbon.library;

import org.carbon.PrettyPrintable;
import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * @author Ethan
 */
public class LessThanExpression extends CarbonExpression {
    private Optional<CarbonExpression> rhs;

    public LessThanExpression(CarbonExpression parent){
        super(parent, parent.getMember("Boolean"));
        rhs = Optional.empty();
    }

    public LessThanExpression(CarbonExpression parent, CarbonExpression rhs){
        super(parent, parent.getMember("Boolean"));
        this.rhs = Optional.of(rhs);
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.empty();
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (rhs.isPresent()){
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization" + this + "\n" + parameter);
        }
        return new LessThanExpression(getParent(), parameter.link(this));
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
}
