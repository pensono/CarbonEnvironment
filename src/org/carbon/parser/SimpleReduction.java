package org.carbon.parser;

import org.carbon.TransparentStack;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Ethan
 */
public class SimpleReduction extends Reduction {
    private Predicate<SyntaxNode>[] accepts;
    private Function<SyntaxNode[], SyntaxNode> rule;

    public SimpleReduction(Function<SyntaxNode[], SyntaxNode> rule, Predicate<SyntaxNode>... accepts) {
        this.accepts = accepts;
        this.rule = rule;
    }

    @Override
    public boolean accepts(TransparentStack<SyntaxNode> stack) {
        if (stack.size() < accepts.length) {
            return false;
        }

        for (int i = 0; i < accepts.length; i++) {
            if (!accepts[accepts.length - 1 - i].test(stack.peek(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public void apply(TransparentStack<SyntaxNode> stack) {
        SyntaxNode[] nodes = new SyntaxNode[accepts.length];
        for (int i = 0; i < accepts.length; i++){
            nodes[i] = stack.pop();
        }
        stack.push(rule.apply(nodes));
    }
}