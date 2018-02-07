package org.carbon.compiler;

import org.carbon.parser.*;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.runtime.ModifiableScope;
import org.carbon.tokenizer.TokenIterator;
import org.carbon.tokenizer.Tokenizer;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class Compiler {
    public static CarbonExpression compileExpression(CarbonScope scope, String input) {
        RecursiveDescentParser parser = new RecursiveDescentParser();
        TokenIterator tokens = new TokenIterator(Tokenizer.tokenize(input));

        ExpressionNode syntax = parser.parseExpression(tokens);
        //System.out.println(syntax.getFullString());

        if (tokens.hasNext()) {
            throw new ParseException("End of expression reached while input is remaining", tokens.nextToken());
        }

        CarbonExpression expression = syntax.linkExpression(scope);
        //System.out.println(expression.getFullString());

        return expression.reduce();
    }

    public static void compileStatementsInto(ModifiableScope scope, String input) {
        RecursiveDescentParser parser = new RecursiveDescentParser();
        TokenIterator tokens = new TokenIterator(Tokenizer.tokenize(input));

        CompoundExpressionNode syntax = parser.parseStatements(tokens);
        if (tokens.hasNext()) {
            throw new ParseException("End of expression reached while input is remaining", tokens.nextToken());
        }

        CompoundExpression expression = syntax.linkExpression(scope);

        // Eventually order shouldn't matter here
        for (String memberName : expression.getMemberNames()) {
            scope.addMember(memberName, expression.getMember(memberName).get());
        }
    }
}

