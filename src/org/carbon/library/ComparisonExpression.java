package org.carbon.library;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.parser.ParseException;
import org.carbon.compiler.PrimeExpression;

import java.util.Optional;
import java.util.function.BiPredicate;

/**
 * @author Ethan
 */
public class ComparisonExpression extends PrimeExpression {
    private IntegerExpression lhs;
    private Optional<IntegerExpression> rhs;
    private String comparisonName;
    private BiPredicate<Integer, Integer> operator;

    private ComparisonExpression(CarbonScope scope, IntegerExpression lhs, Optional<IntegerExpression> rhs, BiPredicate<Integer, Integer> operator, String comparisonName){
        super(scope, new BooleanInterface(scope));
        this.lhs = lhs;
        this.rhs = rhs;
        this.comparisonName = comparisonName;
        this.operator = operator;
    }

    public ComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, IntegerExpression lhs) {
        this(scope, lhs, Optional.empty(), operator, comparisonName);
    }

    public ComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, IntegerExpression lhs, IntegerExpression rhs){
        this(scope, lhs, Optional.of(rhs), operator, comparisonName);
    }

    @Override
    public CarbonExpression apply(CarbonExpression expression) {
        if (rhs.isPresent()){
            //double paramaterization, what happens now?
            throw new ParseException("Double parametrization " + this + "\n" + expression);
        }
        if (!IntegerInterface.isSupertypeOfUnparameterized(expression.getInterface())){
            throw new ParseException("Parameter is not a subtype of Integer\n" + expression.getBodyString());
        }
        return new ComparisonExpression(getScope(), operator, comparisonName, lhs, (IntegerExpression) expression);
    }

//    public String getBodyString(int level) {
//        return PrettyPrintable.indent(level) + getShortString() + "(" +
//                (rhs.isPresent() ? rhs.get().getShortString() : "") + ")";
//    }

    @Override
    public String getShortString() {
        return comparisonName + ":"+ getInterface().getShortString();
    }

    @Override
    public CarbonExpression reduce(){
        if (!rhs.isPresent()){
            return this;
        }

        // TODO does this always reduce to a IntegerExpression?
        rhs = Optional.of((IntegerExpression) rhs.get().reduce());
        if (rhs.isPresent()){
            boolean value = operator.test(lhs.getValue(), rhs.get().getValue());
            return new BooleanExpression(getScope(), value);
        }
        return this;
    }
}
