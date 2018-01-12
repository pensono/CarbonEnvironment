package org.carbon.parser;

import org.carbon.TransparentStack;
import org.carbon.tokenizer.TokenIterator;
import org.carbon.tokenizer.TokenType;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by Ethan Shea on 3/31/2017.
 * TODO: Verify that labels are not grammar tokens
 */
public class ShiftReduceParser {
    public List<Reduction> reductions = new ArrayList<>();
    public TransparentStack<SyntaxNode> parseStack = new TransparentStack<>();

    public ShiftReduceParser(){
        addReduction(nodes -> {
            List<StatementNode> statements = new ArrayList<>();
            for (SyntaxNode node : nodes){
                statements.add((StatementNode) node);
            }
            return new CompoundExpressionNode(statements);
        }, "{", "}", type(StatementNode.class));

        addReduction(nodes -> new StatementNode(((TokenNode)nodes[0]).getToken().getText(), (ExpressionNode) nodes[2])
                , type(TokenNode.class), text("="), type(ExpressionNode.class), text(";"));

        addReduction(nodes -> new StatementNode(((TokenNode)nodes[0]).getToken().getText(), (ExpressionNode) nodes[2])
        , type(TokenNode.class), text("="), type(ExpressionNode.class), text(";"));

        addReduction(nodes -> {
            IdentifierNode identifier = ((IdentifierNode)nodes[0]).narrow(((TokenNode)nodes[1]).getToken().getText());
            return new AppliedExpressionNode(identifier, (ExpressionNode) nodes[2]);
        }, type(IdentifierNode.class), token(TokenType.SYMBOL), type(ExpressionNode.class));

        addReduction(nodes -> {
            IdentifierNode identifier = ((IdentifierNode)nodes[0]).narrow(((TokenNode)nodes[1]).getToken().getText());
            return new AppliedExpressionNode(identifier, (ExpressionNode) nodes[2]);
        }, type(IdentifierNode.class), token(TokenType.SYMBOL), type(ExpressionNode.class));

        addReduction(nodes -> ((IdentifierNode)nodes[0]).narrow(((TokenNode)nodes[2]).getToken().getText()),
                type(IdentifierNode.class), text("."), type(TokenNode.class));
        addReduction(nodes -> new IdentifierNode(((TokenNode)nodes[0]).getToken().getText()),
                type(TokenNode.class));
    }

    public StatementNode parseStatement(TokenIterator tokens) {
        while (tokens.hasNext()) {
            parseStack.push(new TokenNode(tokens.nextToken()));

            boolean hasParsed = true;
            while (hasParsed) {
                hasParsed = false;
                for (Reduction  reduction : reductions) {
                    if (reduction.accepts(parseStack)) {
                        reduction.apply(parseStack);
                        hasParsed = true;
                        break;
                    }
                }
            }
        }

        if (parseStack.peek(0) instanceof StatementNode) {
            return ((StatementNode)parseStack.peek(0));
        } else {
            throw new ParseException("Something went wrong.");
        }
    }

    private static Predicate<SyntaxNode> text(String expected) {
        return (SyntaxNode node) -> (node instanceof TokenNode) && (((TokenNode) node).getToken().getText().equals(expected));
    }

    private static Predicate<SyntaxNode> token(TokenType expected) {
        return (SyntaxNode node) -> (node instanceof TokenNode) && (((TokenNode) node).getToken().getType() == expected);
    }

    private static Predicate<SyntaxNode> type(Class<? extends SyntaxNode> type) {
        return type::isInstance;
    }

    private void addReduction(Function<SyntaxNode[], SyntaxNode> rule, Predicate<SyntaxNode>... accepts){
        reductions.add(new SimpleReduction(rule, accepts));
    }

    private void addReduction(Function<List<SyntaxNode>, SyntaxNode> rule, String start, String end, Predicate<SyntaxNode> accepts){
        reductions.add(new ListReduction(rule, start, end, accepts));
    }

    public CompoundExpressionNode parseStatements(TokenIterator tokens) {
        List<StatementNode> statements = new ArrayList<>();
        while (tokens.hasNext()) {
            statements.add(parseStatement(tokens));
        }

        return new CompoundExpressionNode(statements);
    }
}
