package org.carbon.parser;

import org.carbon.library.IntegerLiteralExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

/**
 * @author Ethan
 */
public class NumericNode extends ExpressionNode {
    int value; // floats/other types eventually

    public NumericNode(int value) {
        this.value = value;
    }

    @Override
    public CarbonExpression link(CarbonScope scope) {
        return new IntegerLiteralExpression(scope, value);
    }

    @Override
    public String getShortString() {
        return "NumericNode: " + value;
    }
}
