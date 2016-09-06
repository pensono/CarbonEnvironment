package org.carbon.compiler;

import com.google.common.base.Strings;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeMemberExpression extends PrototypeExpression {
    private PrototypeExpression base;
    private String memberName;

    public PrototypeMemberExpression(PrototypeExpression base, String memberName) {
        this.base = base;
        this.memberName = memberName;
    }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString() + "\n" +
                base.getPrettyString(level + 1) + "\n" +
                Strings.repeat("  ", level + 1  ) + "Member Name: " + memberName;
    }

    @Override
    public String getDebugString() {
        return "Member Expression";
    }
}
