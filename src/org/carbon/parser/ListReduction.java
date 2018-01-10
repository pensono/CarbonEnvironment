package org.carbon.parser;

import org.carbon.TransparentStack;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Ethan
 */
public class ListReduction extends Reduction{
    private String start;
    private String end;
    private Predicate<SyntaxNode> middle;
    private Function<List<SyntaxNode>, SyntaxNode> rule;

    public ListReduction(Function<List<SyntaxNode>, SyntaxNode> rule, String start, String end, Predicate<SyntaxNode> middle) {
        this.start = start;
        this.end = end;
        this.middle = middle;
        this.rule = rule;
    }

    @Override
    public boolean accepts(TransparentStack<SyntaxNode> stack) {
        if (stack.size() < 2) {
            return false;
        }

        if (!tokenMatches(stack.peek(0), end)){
            return false;
        }

        int index = 1;
        while (!(tokenMatches(stack.peek(index), start)) && index + 2 < stack.size()){
            if (!middle.test(stack.peek(index))) {
                return false;
            }
            index++;
        }


        if (!(tokenMatches(stack.peek(index + 1), start))){
            return false;
        }

        return true;
    }

    private boolean tokenMatches(SyntaxNode node, String end) {
        return (node instanceof TokenNode) && ((TokenNode) node).getToken().getText().equals(end);
    }

    @Override
    public void apply(TransparentStack<SyntaxNode> stack) {

    }
}
