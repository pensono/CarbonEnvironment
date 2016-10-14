package org.carbon.compiler;

import com.google.common.base.Strings;
import org.carbon.PrettyPrintable;

import java.util.Optional;

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
        return PrettyPrintable.indent(level) + getDebugString() + "\n" +
                base.getPrettyString(level + 1) + "\n" +
                PrettyPrintable.indent(level + 1) + "Member Name: " + memberName;
    }

    @Override
    public String getDebugString() {
        return "Member Expression";
    }

    @Override
    public CarbonExpression link(CarbonExpression scope) {
        CarbonExpression baseExpr = base.link(scope);
        Optional<CarbonExpression> memberExpr = baseExpr.getMember(memberName);
        if (memberExpr.isPresent())
            return memberExpr.get();
        throw new ParseException("Member " + memberExpr + "not found.");
    }
}
