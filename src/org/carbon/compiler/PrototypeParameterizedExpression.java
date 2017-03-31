package org.carbon.compiler;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeParameterizedExpression extends PrototypeExpression {
    private PrototypeExpression base;
    private List<PrototypeExpression> parameterList = new ArrayList<>();

    public PrototypeParameterizedExpression(PrototypeExpression base, List<PrototypeExpression> parameterList) {
        this.parameterList = parameterList;
        this.base = base;
    }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getShortString() + "\n" +
                base.getPrettyString(level + 1) + "\n" +
                String.join("\n", parameterList.stream().map(p -> p.getPrettyString(level + 1)).collect(Collectors.toList()));
    }

    @Override
    public String getShortString() {
        return "Parameter Expression";
    }

    @Override
    public CarbonExpression link(CarbonExpression scope) {
        CarbonExpression expr = base.link(scope);
        for (PrototypeExpression expression : parameterList) {
            expr = expr.parameteritize(expression);
        }
        return expr;
    }
}
