package org.carbon.compiler;

import com.google.common.base.Strings;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class CompositeExpression extends CarbonExpression{
    private CarbonExpression base;
    private CarbonExpression operator;
    private CarbonExpression parameter;

    public CompositeExpression(CarbonExpression base, CarbonExpression operator, CarbonExpression parameter) {
        this.base = base;
        this.operator = operator;
        this.parameter = parameter;
    }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString() + "\n" +
                base.getPrettyString(level + 1) + "\n" +
                operator.getPrettyString(level + 1) + "\n" +
                parameter.getPrettyString(level + 1) + "\n";
    }

    @Override
    public String getDebugString() {
        return "Composite Expression";
    }
}
