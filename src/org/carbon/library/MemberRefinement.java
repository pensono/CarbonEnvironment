package org.carbon.library;

import org.carbon.parser.ExpressionNode;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.List;

/**
 * @author Ethan
 */
public class MemberRefinement extends CarbonInterface {
    private CarbonInterface baseInterface;
    private String memberName;
    private List<CarbonExpression> arguments;

    public MemberRefinement(CarbonScope scope, CarbonInterface baseInterface, String memberName, List<CarbonExpression> arguments) {
        super(scope);
        this.baseInterface = baseInterface;
        this.memberName = memberName;
        this.arguments = arguments;
    }

    public boolean isSubtypeOf(CarbonInterface carbonInterface) {
        return baseInterface.isSubtypeOf(carbonInterface);
    }

    public CarbonExpression evaluate(CarbonExpression base){
        CarbonExpression expression = base.getMember(memberName).get();

        for (CarbonExpression arg : arguments) {
            expression = expression.apply(arg);
        }

        return expression.reduce();
    }
}
