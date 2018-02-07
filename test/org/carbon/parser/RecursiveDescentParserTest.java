package org.carbon.parser;

import org.carbon.compiler.Compiler;
import org.carbon.compiler.CompoundExpression;
import org.carbon.compiler.LinkException;
import org.carbon.library.CarbonLibrary;
import org.carbon.library.IntegerLiteralExpression;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.runtime.ModifiableScope;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class RecursiveDescentParserTest {
    ModifiableScope scope;

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

    @Test
    public void noValueParameterized() throws Exception {
        Compiler.compileStatementsInto(scope, "Test = { Member : Integer; };");

        CompoundExpression expr = (CompoundExpression) scope.getMember("Test").get();
        assertEquals(1, expr.getInterface().getArity());
        // assertTrue(expr.getMember("Member").isPresent()); // Not sure how I want to implement this yet...
    }

    @Test
    public void parseMultiDigitNumber() throws Exception {
        assertEquals(1234, compileIntExpression("1234"));
    }

    @Test
    public void parseNegativeNumber() throws Exception {
        assertEquals(-10, compileIntExpression("-10"));
        assertEquals(-1234, compileIntExpression("-1234"));
    }

    public int compileIntExpression(String expression) {
        CarbonExpression value = Compiler.compileExpression(scope, expression);
        return ((IntegerLiteralExpression) value).getValue();
    }

    @Test(expected = ParseException.class)
    public void needsSemicolonEndOfInput() throws Exception {
        Compiler.compileStatementsInto(scope, "Test = 1234");
    }

    @Test(expected = ParseException.class)
    public void expressionFailsAsStatement() throws Exception {
        Compiler.compileStatementsInto(scope, "Test { Integer; }");
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