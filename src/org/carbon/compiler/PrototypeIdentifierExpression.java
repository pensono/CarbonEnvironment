package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public class PrototypeIdentifierExpression extends PrototypeExpression {
    private String name;

    public PrototypeIdentifierExpression(String name) {
        this.name = name;
    }

    @Override
    public String getDebugString() {
        return "Identifier: " + name;
    }
}
