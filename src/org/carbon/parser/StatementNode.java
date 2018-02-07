package org.carbon.parser;

import java.util.Optional;

/**
 * @author Ethan
 */
public class StatementNode extends SyntaxNode {
    private String label;
    private Optional<TypeNode> type;
    private Optional<ExpressionNode> value;

    public StatementNode(String label, Optional<TypeNode> type, Optional<ExpressionNode> value) {
        this.label = label;
        this.type = type;
        this.value = value;
    }

    public StatementNode(String label, ExpressionNode value) {
        this(label, Optional.empty(), Optional.of(value));
    }

    public StatementNode(String label, TypeNode type, ExpressionNode value) {
        this(label, Optional.of(type), Optional.of(value));
    }

    public String getLabel() {
        return label;
    }

    public Optional<TypeNode> getLhsType() {
        return type;
    }

    public Optional<ExpressionNode> getRhs() {
        return value;
    }

    @Override
    public String getShortString() {
        return null;
    }
}
