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

    public CarbonExpression compile(CarbonExpression scope, String input) {
        List<Token> tokens = tokenize(input);
        // System.out.println(String.join(" ",tokens));
        PrototypeExpression protypeExpression = parser.parseExpression(new TokenIterator(tokens));
        // System.out.println(protypeExpression.getPrettyString());
        CarbonExpression expression = link(scope, protypeExpression);
        // System.out.println(expression.getPrettyString());
        expression = expression.reduce();
        //System.out.println(expression.getPrettyString());
        return expression;
    }

    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        String[] lines = input.split("\\n");
        for (int line = 0; line < lines.length; line++) {
            final int lineNo = line + 1; // For the closure. Line numbers start counting at 1
            tokens.addAll(Arrays.asList(lines[line].split("\\s+|(?=" + grammarChars + ")|(?<=" + grammarChars + ")")).stream()
                .filter(s -> !s.isEmpty())
                .map(s -> new Token(s, lineNo, 0)) // TODO implement the character position instead of just 0
                .collect(Collectors.toList()));
        }
        return tokens;
    }


    private CarbonExpression link(CarbonExpression scope, PrototypeExpression protypeExpression) {
        return protypeExpression.link(scope);
    }
}

