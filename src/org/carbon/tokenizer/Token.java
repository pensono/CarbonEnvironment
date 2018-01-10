package org.carbon.tokenizer;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Token {
    private String token;
    private String line;
    private int lineNumber;
    private int columnNumber;

    public Token(String token, String line, int lineNumber, int columnNumber) {
        if (token.isEmpty()) {
            throw new IllegalArgumentException("Token can't be empty.");
        }

        this.token = token;
        this.line = line;
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
    }

    public String getText() {
        return token;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public String toString() {
        return "[" + token + " " + lineNumber + ":" + columnNumber + "]";
    }

    public String getLine() {
        return line;
    }

    public TokenType getType() {
        if (Tokenizer.grammarChars.contains(token.charAt(0) + "")) {
            return TokenType.GRAMMAR;
        } else if (Character.isAlphabetic(token.charAt(0))) {
            return TokenType.REGULAR;
        } else if (Character.isLetterOrDigit(token.charAt(0))) {
            return  TokenType.NUMERIC;
        } else {
            return TokenType.SYMBOL;
        }
    }
}
