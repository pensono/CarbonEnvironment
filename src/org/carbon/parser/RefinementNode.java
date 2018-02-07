package org.carbon.parser;

import org.carbon.PrettyPrintable;
import org.carbon.library.MemberRefinement;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class RefinementNode extends SyntaxNode {
    private String identifier;
    private List<ExpressionNode> arguments;

    public RefinementNode(String identifier, List<ExpressionNode> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public RefinementNode(String identifier) {
        this(identifier, Collections.emptyList());
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    public MemberRefinement linkRefinement(CarbonScope scope, CarbonInterface baseInterface) {
        List<CarbonExpression> expressionArguments = arguments.stream()
                .map(a -> a.linkExpression(scope)).collect(Collectors.toList());
        return new MemberRefinement(scope, baseInterface, identifier, expressionArguments);
    }

    @Override
    public String getShortString() {
        return "RefinementNode: " + identifier;
    }

    @Override
    public String getBodyString(int level) {
        return "Arguments: \n" + PrettyPrintable.fullString(arguments, level + 1);
    }
}
