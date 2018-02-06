package org.carbon.parser;

import org.carbon.compiler.Compiler;
import org.carbon.compiler.LinkException;
import org.carbon.library.CarbonLibrary;
import org.carbon.library.IntegerLiteralExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class RecursiveDescentParserTest {
    CarbonScope scope;

    @Before
    public void setup(){
        scope = new CarbonLibrary();
    }

    @Test
    public void parseExpressionDotApply() throws Exception {
        assertEquals(6, compileIntExpression("4.+(2)"));
    }

    @Test
    public void parseExpressionMultipleDotApply() throws Exception {
        assertEquals(11, compileIntExpression("4.*(2).+(3)"));
    }

    @Test
    public void parseExpressionMultipleSymbols() throws Exception {
        assertEquals(6, compileIntExpression("1 + 2 + 3"));
    }

    @Test(expected = LinkException.class)
    public void dotDoesNotAccessScopeAbove() throws Exception {
        CarbonExpression sum = Compiler.compileExpression(scope, "1.Integer");
    }


    @Test
    public void parseParentheses() throws Exception {
        assertEquals(3, compileIntExpression("(1 + 2)"));
    }

    @Test
    public void operationAfterParentheses() throws Exception {
        assertEquals(6, compileIntExpression("(1 + 2) + 3"));
    }

    @Test
    public void parenthesesAfterOperation() throws Exception {
        assertEquals(6, compileIntExpression("1 + (2 + 3)"));
    }

    @Test
    public void operationsLeftAssociative() throws Exception {
        assertEquals(11, compileIntExpression( "4 * 2 + 3"));
    }

    @Test(expected = ParseException.class)
    public void failSymbolMissingOperand() throws Exception {
        Compiler.compileExpression(scope, "2 +");
    }

    @Test
    public void explicitlyTypedAssignment() throws Exception {
        Compiler.compileStatementsInto(scope, "Test : Integer = 4;");

        assertEquals(4, ((IntegerLiteralExpression) scope.getMember("Test").get()).getValue());
    }

    @Test
    public void refinementType() throws Exception {
        Compiler.compileStatementsInto(scope, "Test : Integer[< 5] = 4;");

        assertEquals(4, ((IntegerLiteralExpression) scope.getMember("Test").get()).getValue());
    }

    public int compileIntExpression(String expression) {
        CarbonExpression value = Compiler.compileExpression(scope, expression);
        return ((IntegerLiteralExpression) value).getValue();
    }

    // Uncomment when refinements/a type system are implemented
//    @Test
//    public void refinementParsed() throws Exception {
//        CarbonScope scope = new CarbonLibrary();
//        Compiler.compileStatementsInto(scope, "Test : Integer[< 5] = 4;");
//
//        assertEquals(4, ((IntegerLiteralExpression) expr).getRhs());
//    }
}