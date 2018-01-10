package org.carbon.tokenizer;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import org.carbon.parser.ParseException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class TokenIterator implements PeekingIterator<String> {
    private PeekingIterator<Token> impl;

    public TokenIterator(List<Token> tokens){
        System.out.println(tokens.stream().map(Token::getText).collect(Collectors.joining(",")));
        impl = Iterators.peekingIterator(tokens.iterator());
    }

    public void consume(String tokenStr){
        Token token = impl.next();
        if (!token.getText().equals(tokenStr))
            throw new ParseException("Expected a " + tokenStr + " at line:col " + token.getLineNumber() +
                    ":" + token.getColumnNumber() + ". Instead got a " + token.getText() + "\n" + token.getLine());
    }

    @Override
    public String peek() {
        try {
            return impl.peek().getText();
        } catch (NoSuchElementException ex){
            throw new ParseException("End of file reached.");
        }
    }

    public Token peekToken() {
        try {
            return impl.peek();
        } catch (NoSuchElementException ex){
            throw new ParseException("End of file reached.");
        }
    }

    @Override
    public boolean hasNext() {
        return impl.hasNext();
    }

    @Override
    public String next() {
        return impl.next().getText();
    }

    public Token nextToken() {
        return impl.next();
    }

    @Override
    public void remove() {
        impl.remove();
    }

    // TODO make a way to obtain the line and column of the current token for better diagnostics

}
