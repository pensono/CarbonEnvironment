package org.carbon.compiler;

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

    public String getBodyString(int level) {
        return PrettyPrintable.indent(level) + "Member Name: " + memberName + "\n" +
                PrettyPrintable.indent(level) + "Base: " + base.getShortString() + PrettyPrintable.bodyWithReturn(base);
    }

    @Override
    public String getShortString() {
        return "Member Expression";
    }

    @Override
    public CarbonExpression link(CarbonExpression scope) {
        CarbonExpression baseExpr = base.link(scope);
        Optional<CarbonExpression> memberExpr = baseExpr.getMember(memberName);
        if (memberExpr.isPresent())
            return memberExpr.get();
        throw new ParseException("Member " + memberName + " not found in\n" + base.getBodyString());
    }
}
