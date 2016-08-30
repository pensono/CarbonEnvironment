package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class IntegerExpression extends TerminalExpression{
    private int value;

    public IntegerExpression(int value) {
        this.value = value;
    }

    @Override
    public String getDebugString() {
        return "Integer: " + value;
    }
}
