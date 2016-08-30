package org.carbon.compiler;

import com.google.common.collect.PeekingIterator;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerParselet extends Parselet {
    @Override
    public CarbonExpression parse(PeekingIterator<String> tokens) {
        return new IntegerExpression(Integer.parseInt(tokens.next()));
    }
}
