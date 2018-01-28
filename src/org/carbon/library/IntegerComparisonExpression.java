package org.carbon.library;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;
import org.carbon.parser.ParseException;
import org.carbon.compiler.PrimeExpression;

import java.util.*;
import java.util.function.BiPredicate;

/**
 * @author Ethan
 */
public class IntegerComparisonExpression extends PrimeExpression {
    private IntegerExpression lhs;
    private Optional<IntegerExpression> rhs;
    private String comparisonName;
    private BiPredicate<Integer, Integer> operator;

    // The way the parameterInterfaces is handled is bad
    private IntegerComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName,
                                        IntegerExpression lhs, Optional<IntegerExpression> rhs, List<CarbonInterface> parameterInterfaces){
        super(scope, new BooleanInterface(scope, parameterInterfaces));
        this.lhs = lhs;
        this.rhs = rhs;
        this.comparisonName = comparisonName;
        this.operator = operator;
    }

    public IntegerComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, IntegerExpression lhs) {
        this(scope, operator, comparisonName, lhs, Optional.empty(), Collections.singletonList(new IntegerInterface(scope)));
    }

    public IntegerComparisonExpression(CarbonScope scope, BiPredicate<Integer, Integer> operator, String comparisonName, IntegerExpression lhs, IntegerExpression rhs){
        this(scope, operator, comparisonName, lhs, Optional.of(rhs), Collections.emptyList());
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
        return new IntegerComparisonExpression(getScope(), operator, comparisonName, lhs, (IntegerExpression) expression);
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

        // TODO does this always reduce to a IntegerLiteralExpression?
        rhs = Optional.of((IntegerExpression) rhs.get().reduce());
        if (rhs.isPresent() && rhs.get() instanceof IntegerLiteralExpression && lhs instanceof IntegerLiteralExpression){
            boolean value = operator.test(((IntegerLiteralExpression)lhs).getValue(), ((IntegerLiteralExpression)rhs.get()).getValue());
            return new BooleanExpression(getScope(), value);
        }
        return this;
    }
}
