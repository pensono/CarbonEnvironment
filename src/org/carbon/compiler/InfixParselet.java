package org.carbon.compiler;

import com.google.common.collect.PeekingIterator;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class InfixParselet {

    public CarbonExpression parse(PeekingIterator<String> tokens, CarbonExpression base) {
        String operatorToken = tokens.next();
        CarbonExpression parameter = Compiler.parse(tokens);

        CarbonExpression operator;
        switch (operatorToken) {
            case "<":
                operator = new CarbonExpression() {
                    @Override
                    public String getDebugString() {
                        return "< Operator";
                    }
                };
                break;
            default:
                throw new ParseException("Invalid Token");
        }

        return new CompositeExpression(base, operator, parameter);
    }
}
