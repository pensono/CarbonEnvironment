package org.carbon.compiler;

import com.google.common.primitives.Ints;

import java.util.*;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    private static List<Parselet> terminalParselets = new ArrayList<>();
    private static List<CompoundParselet> compoundParselets = new ArrayList<>();
    static {
        terminalParselets.add(new Parselet() {
            @Override
            public PrototypeExpression parse(TokenIterator tokens) {
                return new PrototypeIntegerExpression(Integer.parseInt(tokens.next()));
            }

            @Override
            public boolean testMatch(String token) {
                return Ints.tryParse(token) != null;
            }
        });
        terminalParselets.add(new Parselet() {
            @Override
            public PrototypeExpression parse(TokenIterator tokens) {
                return new PrototypeIdentifierExpression(tokens.next());
            }

            @Override
            public boolean testMatch(String token) {
                return true;
            }
        });
        compoundParselets.add(new StaticParselet(".") {
            @Override
            public PrototypeExpression parse(PrototypeExpression base, TokenIterator tokens) {
                tokens.consume(".");
                PrototypeExpression member = Compiler.parse(tokens);
                return new PrototypeMemberExpression(base, member);
            }
        });
    }

    public static CarbonExpression compile(String input){
        List<String> tokens = tokenize(input);
        System.out.println(String.join(",",tokens));
        PrototypeExpression protypeExpression = parse(new TokenIterator(tokens));
        System.out.println(protypeExpression.getPrettyString());
        return null;
    }

    public static List<String> tokenize(String input) {
        return Arrays.asList(input.split(" |(?=[.])|(?<=[.])"));
    }

    public static PrototypeExpression parse(TokenIterator tokens) {
        //See if it's an infix operator
        String token = tokens.peek();

        Optional<Parselet> match = terminalParselets.stream().filter(p -> p.testMatch(token)).findFirst();
        if (match.isPresent()){
            PrototypeExpression base = match.get().parse(tokens);

            if (!tokens.hasNext()) return base;
            final String nextToken = tokens.peek();

            Optional<CompoundParselet> nextMatch = compoundParselets.stream().filter(p -> p.testMatch(nextToken)).findFirst();
            if (match.isPresent()){
                return nextMatch.get().parse(base,tokens);
            }
            return base;
        } else {
            throw new ParseException("Invalid token: " + token);
        }
    }
}

