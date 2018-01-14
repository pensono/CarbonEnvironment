package org.carbon.library;

import org.carbon.compiler.Compiler;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class IntegerArithmeticExpressionTest {

    @Test
    public void rightSimplify() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "(1 + 2) + 3");

        assertEquals(6, ((IntegerLiteralExpression) sum).getValue());
    }
}