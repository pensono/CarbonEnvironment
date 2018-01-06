package org.carbon.compiler;

import org.carbon.tokenizer.TokenIterator;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class Parselet {
    public abstract PrototypeExpression parse(TokenIterator tokens);
    public abstract boolean testMatch(String token);
}
