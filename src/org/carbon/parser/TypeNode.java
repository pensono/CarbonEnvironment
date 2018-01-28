package org.carbon.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan
 */
public class TypeNode {
    private IdentifierNode identifier;
    private List<ExpressionNode> refinements;


    public TypeNode(IdentifierNode identifier) {
        this(identifier, new ArrayList<>());
    }

    public TypeNode(IdentifierNode identifier, List<ExpressionNode> refinements) {
        this.identifier = identifier;
        this.refinements = refinements;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<ExpressionNode> getRefinements() {
        return refinements;
    }
}
