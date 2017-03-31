package org.carbon.compiler;

import com.google.common.primitives.Ints;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    public static final String grammarChars = "[\\{}.\\(\\),]";
    private Parser parser = new PrattParser();

    public CarbonExpression compile(CarbonExpression scope, String input){
        List<String> tokens = tokenize(input);
        // System.out.println(String.join(" ",tokens));
        PrototypeExpression protypeExpression = parser.parseExpression(new TokenIterator(tokens));
        // System.out.println(protypeExpression.getPrettyString());
        CarbonExpression expression = link(scope, protypeExpression);
        // System.out.println(expression.getPrettyString());
        expression = expression.reduce();
        //System.out.println(expression.getPrettyString());
        return expression;
    }

    public List<String> tokenize(String input) {
        return Arrays.asList(input.split("\\s+|(?=" + grammarChars + ")|(?<=" + grammarChars + ")")).stream()
                .filter(s -> !s.isEmpty()).collect(Collectors.toList());
    }


    private CarbonExpression link(CarbonExpression scope, PrototypeExpression protypeExpression) {
        return protypeExpression.link(scope);
    }
}

