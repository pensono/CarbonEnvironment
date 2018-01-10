package org.carbon;

import org.carbon.parser.SyntaxNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan
 */
public class TransparentStack<T> {
    private List<T> backing = new ArrayList<T>();

    public void push(T element) {
        backing.add(element);
    }

    public T pop() {
        return backing.remove(backing.size() - 1);
    }

    public T peek(int index) {
        return backing.get(backing.size() - 1 - index);
    }

    public int size(){
        return backing.size();
    }
}
