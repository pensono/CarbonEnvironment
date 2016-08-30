package org.carbon.compiler;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import com.google.common.primitives.Ints;

import java.util.*;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    private static Map<String, InfixParselet> infixParselets = new HashMap<>();
    static {
        infixParselets.put("<", new InfixParselet());
    }

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
        Parselet parselet = matchParselet(token);

        CarbonExpression base = parselet.parse(tokens);

        if (!tokens.hasNext()) return base;
        String infixToken = tokens.peek();
        if (infixToken.equals("<")){
            return new InfixParselet().parse(tokens, base);
        }
        return base;
    }

    private static Parselet matchParselet(String token) {
        if (Ints.tryParse(token) != null){
            return new IntegerParselet();
        } else {
            throw new ParseException("Invalid token: " + token);
        }
    }
}

