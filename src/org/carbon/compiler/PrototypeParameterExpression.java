package org.carbon.compiler;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeParameterExpression extends PrototypeExpression {
    private List<PrototypeExpression> parameterList = new ArrayList<>();

    public PrototypeParameterExpression(List<PrototypeExpression> parameterList) {
        this.parameterList = parameterList;
    }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString() + "\n" +
                String.join("\n", parameterList.stream().map(PrototypeExpression::getDebugString).collect(Collectors.toList()));
    }

    @Override
    public String getDebugString() {
        return "Member Expression";
    }
}
