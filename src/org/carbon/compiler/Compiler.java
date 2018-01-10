package org.carbon.compiler;

import org.carbon.parser.ExpressionNode;
import org.carbon.parser.ShiftReduceParser;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.parser.CompoundExpressionNode;
import org.carbon.parser.RecursiveDescentParser;
import org.carbon.tokenizer.TokenIterator;
import org.carbon.tokenizer.Tokenizer;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    public static CarbonExpression compile(CarbonScope scope, String input) {
        RecursiveDescentParser parser = new RecursiveDescentParser();
        TokenIterator tokens = new TokenIterator(Tokenizer.tokenize(input));

        ExpressionNode rootExpression = parser.parseExpression(tokens);

        CarbonExpression expression = rootExpression.link(scope);

        expression = expression.reduce();
        return expression;
    }

    public static CarbonExpression compileFile(CarbonScope scope, String input) {
        RecursiveDescentParser parser = new RecursiveDescentParser();
        TokenIterator tokens = new TokenIterator(Tokenizer.tokenize(input));

        CompoundExpressionNode rootExpression = parser.parseStatements(tokens);

        CarbonExpression expression = rootExpression.link(scope);

        expression = expression.reduce();
        return expression;
    }
}

