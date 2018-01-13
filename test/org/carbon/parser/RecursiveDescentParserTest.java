package org.carbon.parser;

import org.carbon.compiler.Compiler;
import org.carbon.library.BooleanExpression;
import org.carbon.library.CarbonLibrary;
import org.carbon.library.IntegerExpression;
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

        assertEquals(((IntegerExpression) sum).getValue(), 6);
    }

}