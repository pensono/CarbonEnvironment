package org.carbon.compiler;

import org.carbon.tokenizer.TokenIterator;

/**
 * Created by Ethan Shea on 3/31/2017.
 */
public abstract class Parser {
    public abstract PrototypeExpression parseExpression(TokenIterator tokenIterator);
    public abstract PrototypeExpression parseStatement(TokenIterator tokenIterator);
}
