package org.carbon.compiler;

import com.google.common.base.Strings;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeMemberExpression extends PrototypeExpression {
    private PrototypeExpression base;
    private PrototypeExpression member;

    public PrototypeMemberExpression(PrototypeExpression base, PrototypeExpression member) {
        this.base = base;
        this.member = member;
    }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString() + "\n" +
                base.getPrettyString(level + 1) + "\n" +
                member.getPrettyString(level + 1) + "\n";
    }

    @Override
    public String getDebugString() {
        return "Member Expression";
    }
}
