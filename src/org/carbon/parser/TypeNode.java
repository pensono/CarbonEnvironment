package org.carbon.parser;

/**
 * @author Ethan
 */
public class TypeNode {
    private IdentifierNode identifier;
    // Refinement


    public TypeNode(IdentifierNode identifier) {
        this.identifier = identifier;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }
}
