package org.carbon.parser;

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
                return parseValueExpression(tokens);
        }
    }

    private ExpressionNode parseValueExpression(TokenIterator tokens) {
        ExpressionNode expression = parseTermExpression(tokens);
        while (tokens.hasNext() && tokens.peekToken().getType() == TokenType.SYMBOL) {
            String operator = tokens.next();

            ExpressionNode argument = parseTermExpression(tokens);

            expression = new MemberNode(expression, new IdentifierNode(operator));
            expression = new AppliedExpressionNode(expression, argument);
        }
        return expression;
    }

    private ExpressionNode parseTermExpression(TokenIterator tokens) {
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

        while (tokens.hasNext() && (tokens.peek().equals(".") || tokens.peek().equals("("))) {
            if (tokens.peek().equals(".")) {
                tokens.consume(".");

                IdentifierNode identifier = parseIdentifier(tokens);

                expression = new MemberNode(expression, identifier);
            } else if (tokens.peek().equals("(")) {
                tokens.consume("(");
                List<ExpressionNode> arguments = new ArrayList<>();
                arguments.add(parseExpression(tokens));
                while (!tokens.peek().equals(")")) {
                    tokens.consume(",");
                    arguments.add(parseExpression(tokens));
                }
                tokens.consume(")");

                expression = new AppliedExpressionNode(expression, arguments);
            }
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
            tokens.consume(":");
            type = Optional.of(parseType(tokens));
        }

        tokens.consume("=");
        ExpressionNode expression = parseExpression(tokens);

        tokens.consume(";");

        return new StatementNode(label, type, expression);
    }

    private TypeNode parseType(TokenIterator tokens) {
        IdentifierNode typeIdentifier = parseIdentifier(tokens);

        List<RefinementNode> refinements = new ArrayList<>();
        if (tokens.peek().equals("[")) {
            tokens.consume("[");

            refinements.add(parseRefinement(tokens));
            while (!tokens.peek().equals("]")) {
                refinements.add(parseRefinement(tokens));
                tokens.consume(",");
            }
            tokens.consume("]");
        }

        return new TypeNode(typeIdentifier, refinements);
    }

    private RefinementNode parseRefinement(TokenIterator tokens) {
        if (tokens.peekToken().getType() == TokenType.SYMBOL) {
            String symbol = tokens.next();
            return new RefinementNode(symbol, Collections.singletonList(parseExpression(tokens)));
        } else {
            String identifier = tokens.next();

            List<ExpressionNode> arguments = new ArrayList<>();
            if (tokens.peek().equals("(")) {
                tokens.consume("(");
                while (!tokens.peek().equals(")")) {
                    arguments.add(parseExpression(tokens));
                    tokens.consume(",");
                }
                tokens.consume(")");
            }

            return new RefinementNode(identifier, arguments);
        }
    }

    public CompoundExpressionNode parseStatements(TokenIterator tokens) {
        List<StatementNode> statements = new ArrayList<>();
        while (tokens.hasNext()) {
            statements.add(parseStatement(tokens));
        }
        return new CompoundExpressionNode(statements);
    }
}
