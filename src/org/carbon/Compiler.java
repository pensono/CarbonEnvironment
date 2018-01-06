package org.carbon;

import org.carbon.parser.CompoundExpressionNode;
import org.carbon.parser.RecursiveDescentParser;
import org.carbon.tokenizer.Token;
import org.carbon.tokenizer.TokenIterator;
import org.carbon.tokenizer.Tokenizer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {

    public static CarbonExpression compile(CarbonScope scope, String input) {
        RecursiveDescentParser parser = new RecursiveDescentParser();
        TokenIterator tokens = new TokenIterator(Tokenizer.tokenize(input));

        CompoundExpressionNode rootExpression = parser.parseStatements(tokens);

        CarbonExpression expression = rootExpression.link(scope);

        expression = expression.reduce();
        return expression;
    }
}

