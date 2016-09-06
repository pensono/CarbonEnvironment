package org.carbon.compiler;

import com.google.common.primitives.Ints;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    public static final String grammarChars = "[.\\(\\),]";

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
                return new PrototypeMemberExpression(base, tokens.next());
            }
        });
        compoundParselets.add(new StaticParselet("(") {
            @Override
            public PrototypeExpression parse(PrototypeExpression base, TokenIterator tokens) {
                tokens.consume("(");
                List<PrototypeExpression> parameters = new ArrayList<>();
                while (!tokens.peek().equals(")")) {
                    parameters.add(Compiler.parse(tokens));
                    if (!tokens.peek().equals(",")) {
                        break;
                    }
                    tokens.consume(",");
                }
                tokens.consume(")");

                return new PrototypeParameterExpression(base, parameters);
            }
        });
        compoundParselets.add(new CompoundParselet() {
            Pattern grammarSymbols = Pattern.compile(Compiler.grammarChars);

            @Override
            public PrototypeExpression parse(PrototypeExpression base, TokenIterator tokens) {
                String operator = tokens.next();
                PrototypeExpression argument = Compiler.parse(tokens);
                return new PrototypeParameterExpression(new PrototypeMemberExpression(base, operator), Arrays.asList(argument));
            }

            @Override
            public boolean testMatch(String token) {
                Matcher matcher = grammarSymbols.matcher(token);
                return !matcher.find();
            }
        });
    }

    public static CarbonExpression compile(String input){
        List<String> tokens = tokenize(input);
        System.out.println(String.join(" ",tokens));
        PrototypeExpression protypeExpression = parse(new TokenIterator(tokens));
        System.out.println(protypeExpression.getPrettyString());
        return null;
    }

    public static List<String> tokenize(String input) {
        return Arrays.asList(input.split(" |(?=" + grammarChars + ")|(?<=" + grammarChars+ ")"));
    }

    public static PrototypeExpression parse(TokenIterator tokens) {
        //See if it's an infix operator
        String token = tokens.peek();

        Optional<Parselet> match = terminalParselets.stream().filter(p -> p.testMatch(token)).findFirst();
        if (match.isPresent()){
            PrototypeExpression base = match.get().parse(tokens);

            // The predicate here must be more strict when the REPL is no longer the only means of input
            while (tokens.hasNext()) {
                final String nextToken = tokens.peek();

                Optional<CompoundParselet> nextMatch = compoundParselets.stream().filter(p -> p.testMatch(nextToken)).findFirst();
                if (nextMatch.isPresent()) {
                    base = nextMatch.get().parse(base, tokens);
                } else {
                    break;
                }
            }
            return base;
        } else {
            throw new ParseException("Invalid token: " + token);
        }
    }
}

