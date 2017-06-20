package org.carbon.compiler;

import org.carbon.CarbonExpression;

/**
 * Created by Ethan Shea on 5/12/2017.
 */
public class IdentifierNotFoundException extends LinkException {
    private String identifier;

    public IdentifierNotFoundException(String identifier, CarbonExpression exp) {
        super(identifier + " not found in scope:" + exp.getFullString());
        this.identifier = identifier;
    }

    public String getIdentifier(){
        return identifier;
    }
}
