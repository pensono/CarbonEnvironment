package org.carbon.library;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * @author Ethan
 */
public class ComparisonExpression extends BooleanExpression {
    private GenericIntegerExpression lhs;
    private Optional<GenericIntegerExpression> rhs;
    private String comparisonName;
    private BiPredicate<Integer, Integer> operator;

    private ComparisonExpression(CarbonScope scope, GenericIntegerExpression lhs, Optional<GenericIntegerExpression> rhs, BiPredicate<Integer, Integer> operator, String comparisonName){
        super(scope);
        this.lhs = lhs;
        this.rhs = rhs;
        this.comparisonName = comparisonName;
        this.operator = operator;
    }

    public ComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, GenericIntegerExpression lhs) {
        this(scope, lhs, Optional.empty(), operator, comparisonName);
    }

    public ComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, GenericIntegerExpression lhs, GenericIntegerExpression rhs){
        this(scope, lhs, Optional.of(rhs), operator, comparisonName);
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        if (rhs.isPresent()){
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization" + this + "\n" + parameter);
        }
        CarbonExpression expression = parameter.link(getScope());
        if (!expression.isSubtypeOf(getMember("Integer").get())){
            throw new ParseException("Parameter is not a subtype of Integer\n" + parameter.getBodyString());
        }
        return new ComparisonExpression(getScope(), operator, comparisonName, lhs, (GenericIntegerExpression) expression);
    }

//    public String getBodyString(int level) {
//        return PrettyPrintable.indent(level) + getShortString() + "(" +
//                (rhs.isPresent() ? rhs.get().getShortString() : "") + ")";
//    }

    @Override
    public String getShortString() {
        return comparisonName + ":"+getSupertype().get().getShortString();
    }

    @Override
    public CarbonExpression reduce(){
        if (!rhs.isPresent()){
            return this;
        }

        // TODO does this always reduce to a GenericIntegerExpression?
        rhs = Optional.of((GenericIntegerExpression) rhs.get().reduce());
        if (rhs.isPresent() && lhs instanceof IntegerExpression && rhs.get() instanceof IntegerExpression){
            boolean value = operator.test(((IntegerExpression) lhs).getValue(),
                                          ((IntegerExpression) rhs.get()).getValue());
            return new BooleanExpression(getScope(), value);
        }
        return this;
    }
}
