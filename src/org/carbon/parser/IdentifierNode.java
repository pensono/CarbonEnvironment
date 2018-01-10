package org.carbon.parser;

import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.compiler.LinkException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan
 */
public class IdentifierNode extends ExpressionNode {
    private List<String> labels;

    public IdentifierNode(String label) {
        List<String> labels = new ArrayList<>();
        labels.add(label);
        this.labels = labels;
    }

    public IdentifierNode(List<String> labels) {
        this.labels = labels;
    }

    public CarbonExpression link(CarbonScope scope) {
        String fullString = String.join(".", labels);
        return scope.getByIdentifier(fullString)
                .orElseThrow(() -> new LinkException("Could not find identifier " + fullString));
    }

    /**
     * Returns another IdentifierNode with this label appended to the end
     * @param label
     * @return
     */
    public IdentifierNode narrow(String label) {
        List<String> newLabels = new ArrayList<>(labels);
        newLabels.add(label);
        return new IdentifierNode(newLabels);
    }

    @Override
    public String getShortString() {
        return "Identifier \"" + String.join(".", labels) + "\"";
    }
}
