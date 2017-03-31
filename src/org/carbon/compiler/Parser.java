package org.carbon.compiler;

/**
 * Created by Ethan Shea on 3/31/2017.
 */
public abstract class Parser {
    public abstract PrototypeExpression parseExpression(TokenIterator tokenIterator);
}
