package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeIntegerExpression extends PrototypeExpression {
    private int value;

    public PrototypeIntegerExpression(int value) {
        this.value = value;
    }

    @Override
    public String getDebugString() {
        return Integer.toString(value);
    }
}
