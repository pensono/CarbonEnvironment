package org.carbon.parser;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.compiler.LinkException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class IdentifierNode extends ExpressionNode {
    private List<String> labels;

    public IdentifierNode(List<String> labels) {
        this.labels = labels;
    }

    public CarbonExpression link(CarbonScope scope) {
        String fullString = String.join(".", labels);
        return scope.getByIdentifier(fullString)
                .orElseThrow(() -> new LinkException("Could not find identifier " + fullString));
    }

    @Override
    public String getShortString() {
        return "Identifier \"" + String.join(".", labels) + "\"";
    }
}
