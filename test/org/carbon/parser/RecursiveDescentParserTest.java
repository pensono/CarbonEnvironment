package org.carbon.parser;

import org.carbon.compiler.Compiler;
import org.carbon.compiler.LinkException;
import org.carbon.library.CarbonLibrary;
import org.carbon.library.IntegerLiteralExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class RecursiveDescentParserTest {
    @Test
    public void parseExpressionDotApply() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "4.+(2)");

        assertEquals(6, ((IntegerLiteralExpression) sum).getValue());
    }

    @Test
    public void parseExpressionMultipleSymbols() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "1 + 2 + 3");

        assertEquals(6, ((IntegerLiteralExpression) sum).getValue());
    }

    @Test(expected = LinkException.class)
    public void dotDoesNotAccessScopeAbove() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "1.Integer");
    }


    @Test
    public void parseParentheses() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "(1 + 2)");

        assertEquals(3, ((IntegerLiteralExpression) sum).getValue());
    }

    @Test
    public void operationAfterParenthesies() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "(1 + 2) + 3");

        assertEquals(6, ((IntegerLiteralExpression) sum).getValue());
    }
}