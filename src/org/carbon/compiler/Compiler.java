package org.carbon.compiler;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.common.primitives.Ints;

import java.util.*;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    public static CarbonExpression compile(String input){
        List<String> tokens = tokenize(input);
        return parse(Iterators.peekingIterator(tokens.iterator()));
    }

    public static List<String> tokenize(String input) {
        return Arrays.asList(input.split(" "));
    }

    public static CarbonExpression parse(PeekingIterator<String> tokens) {
        //See if it's an infix operator
        String token = tokens.peek();

        if (Ints.tryParse(token) != null){
            CarbonExpression base =  new IntegerExpression(Integer.parseInt(tokens.next()));

            if (!tokens.hasNext()) return base;
            String infixToken = tokens.peek();
            if (infixToken.equals("<")){
                return parseInfix(tokens, base);
            }
            return base;
        } else {
            throw new ParseException("Invalid token: " + token);
        }
    }

    private static CarbonExpression parseInfix(PeekingIterator<String> tokens, CarbonExpression base) {
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

