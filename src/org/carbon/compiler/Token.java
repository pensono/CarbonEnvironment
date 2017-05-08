package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Token {
    private String token;
    private int line;
    private int column;

    public Token(String token, int line, int column) {
        this.token = token;
        this.line = line;
        this.column = column;
    }

    public String getToken() {
        return token;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
