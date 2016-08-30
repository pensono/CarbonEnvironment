package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Token {
    private String token;
    private int line;
    private int column;

    public Token(String token) {
        this.token = token;
    }
}
