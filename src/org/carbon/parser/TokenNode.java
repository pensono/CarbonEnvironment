package org.carbon.parser;

import org.carbon.tokenizer.Token;

/**
 * @author Ethan
 */
public class TokenNode extends SyntaxNode {
    private Token token;

    public TokenNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    @Override
    public String getShortString() {
        return "TokenNode: " + token;
    }
}
