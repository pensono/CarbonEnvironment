package org.carbon.parser;

import org.carbon.TransparentStack;

/**
 * @author Ethan
 */
public abstract class Reduction {
    public abstract boolean accepts(TransparentStack<SyntaxNode> stack);
    public abstract void apply(TransparentStack<SyntaxNode> stack);
}
