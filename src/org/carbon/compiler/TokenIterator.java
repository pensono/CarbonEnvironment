package org.carbon.compiler;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class TokenIterator implements PeekingIterator<String> {
    private PeekingIterator<String> impl;

    public TokenIterator(List<String> tokens){
        impl = Iterators.peekingIterator(tokens.iterator());
    }

    public void consume(String token){
        if (!impl.next().equals(token))
            throw new ParseException("Expected a " + token);
    }

    @Override
    public String peek() {
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
        return impl.next();
    }

    @Override
    public void remove() {
        impl.remove();
    }
}
