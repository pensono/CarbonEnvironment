package org.carbon.parser;

import java.util.Optional;

/**
 * @author Ethan
 */
public class StatementNode extends SyntaxNode {
    private String label;
    private Optional<TypeNode> type;
    private ExpressionNode value;

    public StatementNode(String label, Optional<TypeNode> type, ExpressionNode value) {
        this.label = label;
        this.type = type;
        this.value = value;
    }

    public StatementNode(String label, ExpressionNode value) {
        this.label = label;
        this.type = Optional.empty();
        this.value = value;
    }

    public StatementNode(String label, TypeNode type, ExpressionNode value) {
        this.label = label;
        this.type = Optional.of(type);
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public Optional<TypeNode> getLhsType() {
        return type;
    }

    public ExpressionNode getRhs() {
        return value;
    }

    @Override
    public String getShortString() {
        return null;
    }
}
