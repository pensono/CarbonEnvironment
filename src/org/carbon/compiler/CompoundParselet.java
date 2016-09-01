package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public abstract class CompoundParselet {
    public abstract PrototypeExpression parse(PrototypeExpression base, TokenIterator tokens);
    public abstract boolean testMatch(String token);
}
