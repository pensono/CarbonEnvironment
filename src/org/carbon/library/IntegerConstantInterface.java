package org.carbon.library;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.Collections;
import java.util.List;

/**
 * @author Ethan
 */
public class IntegerConstantInterface extends IntegerInterface {
    private int value;

    public IntegerConstantInterface(CarbonScope scope, int value) {
        super(scope);
        this.value = value;
    }

    public boolean isSubtypeOf(CarbonInterface _interface){
        if (_interface instanceof MemberRefinement) { // Eww. Don't like this
            CarbonExpression arg = new IntegerLiteralExpression(_interface.getScope(), value);
            CarbonExpression result = ((MemberRefinement) _interface).evaluate(arg);
            return (result instanceof BooleanExpression) && (((BooleanExpression) result).getValue());
        } else {
            return super.isSubtypeOf(_interface);
        }
    }
}
