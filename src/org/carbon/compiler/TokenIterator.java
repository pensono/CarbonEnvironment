package org.carbon.compiler;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class TokenIterator implements PeekingIterator<String> {
    private PeekingIterator<Token> impl;

    public TokenIterator(List<Token> tokens){
        impl = Iterators.peekingIterator(tokens.iterator());
    }

    public void consume(String tokenStr){
        Token token = impl.next();
        if (!token.getToken().equals(tokenStr))
            throw new ParseException("Expected a " + tokenStr + " at line:col " + token.getLine() +
                    ":" + token.getColumn() + ". Instead got a " + token.getToken());
    }

    @Override
    public String peek() {
        try {
            return impl.peek().getToken();
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
        return impl.next().getToken();
    }

    @Override
    public void remove() {
        impl.remove();
    }

    // TODO make a way to obtain the line and column of the current token for better diagnostics

}
