package org.carbon.parser;

import jdk.internal.org.objectweb.asm.tree.LabelNode;
import org.carbon.tokenizer.TokenIterator;
import org.carbon.tokenizer.TokenType;

import java.util.*;

/**
 * Created by Ethan Shea on 3/31/2017.
 * TODO: Verify that labels are not grammar tokens
 */
public class RecursiveDescentParser {
    public ExpressionNode parseExpression(TokenIterator tokens) {
        switch (tokens.peek()){
            case "{":
                return parseCompoundExpression(tokens);
            //case "|":
            // return parseEnumeration(tokens);
            default:
                ExpressionNode expression = parseValueExpression(tokens);
                return expression;
        }
    }

    private ExpressionNode parseValueExpression(TokenIterator tokens) {
        ExpressionNode expression;

        if (tokens.peekToken().getType() == TokenType.NUMERIC) {
            int value = Integer.parseInt(tokens.next()); // Should be able to parse custom numeric types
            expression = new NumericNode(value);
        } else if (tokens.peek().equals("(")) {
            tokens.consume("(");
            expression = parseValueExpression(tokens);
            tokens.consume(")");
        }else {
            expression = parseIdentifier(tokens);
        }

        while (tokens.hasNext() && tokens.peekToken().getType() == TokenType.SYMBOL) {
            String operator = tokens.next();

            ExpressionNode argument = parseValueExpression(tokens);

            expression = new MemberNode(expression, new IdentifierNode(operator));
            expression = new AppliedExpressionNode(expression, argument);
        }

        if (tokens.hasNext() && tokens.peek().equals(".")) {
            tokens.consume(".");

            IdentifierNode identifier = parseIdentifier(tokens);

            expression = new MemberNode(expression, identifier);
        }

        if (tokens.hasNext() && tokens.peek().equals("(")) {
            tokens.consume("(");
            List<ExpressionNode> arguments = new ArrayList<>();
            arguments.add(parseExpression(tokens));
            while (!tokens.peek().equals(")")){
                tokens.consume(",");
                arguments.add(parseExpression(tokens));
            }
            tokens.consume(")");

            return new AppliedExpressionNode(expression, arguments);
        }
        return expression;
    }

    private CompoundExpressionNode parseCompoundExpression(TokenIterator tokens) {
        List<StatementNode> statements = new ArrayList<>();

        tokens.consume("{");
        while (!tokens.peek().equals("}")) {
            statements.add(parseStatement(tokens));
        }
        tokens.consume("}");

        return new CompoundExpressionNode(statements);
    }

    private IdentifierNode parseIdentifier(TokenIterator tokens) {
        List<String> labels = new ArrayList<>();
        labels.add(tokens.next());

        while (tokens.hasNext() && tokens.peek().equals(".")) {
            tokens.consume(".");
            labels.add(tokens.next());
        }

        return new IdentifierNode(labels);
    }

    public StatementNode parseStatement(TokenIterator tokens) {
        String label = tokens.next();

        Optional<TypeNode> type = Optional.empty();
        if (tokens.peek().equals(":")) {
            type = Optional.of(parseType(tokens));
        }

        tokens.consume("=");

        ExpressionNode expression = parseExpression(tokens);

        tokens.consume(";");

        return new StatementNode(label, type, expression);
    }

    private TypeNode parseType(TokenIterator tokens) {
        // TODO: Refinements
        return new TypeNode(parseIdentifier(tokens));
    }

    public CompoundExpressionNode parseStatements(TokenIterator tokens) {
        List<StatementNode> statements = new ArrayList<>();
        while (tokens.hasNext()) {
            statements.add(parseStatement(tokens));
        }
        return new CompoundExpressionNode(statements);
    }
}
