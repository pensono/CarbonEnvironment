package org.carbon.compiler;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public abstract class StaticParselet extends CompoundParselet {
    private String matchToken;

    public StaticParselet(String matchToken) {
        this.matchToken = matchToken;
    }

    @Override
    public boolean testMatch(String token) {
        return token.equals(matchToken);
    }
}
