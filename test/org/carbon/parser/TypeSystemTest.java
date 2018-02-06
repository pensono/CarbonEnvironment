package org.carbon.parser;

import org.carbon.compiler.Compiler;
import org.carbon.compiler.TypeException;
import org.carbon.library.CarbonLibrary;
import org.carbon.runtime.CarbonScope;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class TypeSystemTest {
    CarbonScope scope;

    @Before
    public void setup(){
        scope = new CarbonLibrary();
    }

    @Test
    public void rangeIntegerTypesafety(){
        assertThrows(TypeException.class, compileStatement("Test : Integer[< 4] = 5;"));
        assertThrows(TypeException.class, compileStatement("Test : Integer[> 4] = 3;"));
        assertThrows(TypeException.class, compileStatement("Test : Integer[> 4] = -1;"));
        assertThrows(TypeException.class, compileStatement("Test : Integer[> 4] = 4;"));
    }

    @Test
    public void rangeIntegerTypesafetyComplexRhs(){
        assertThrows(TypeException.class, compileStatement("Test : Integer[< 4] = 2 + 4;"));
        assertThrows(TypeException.class, compileStatement("Test : Integer[> 4] = 3 - 4;"));
        assertThrows(TypeException.class, compileStatement("Test : Integer[> 4] = 4 * 1;"));
    }

    @Test
    public void nonBoolRefinementFails(){
        assertThrows(TypeException.class, compileStatement("Test : Integer[+ 4] = 2;"));
    }

    @Test
    public void applicationTypesafety(){
        assertThrows(TypeException.class, compileExpression("5 + True"));
    }

    private Runnable compileStatement(String text){
        return () -> Compiler.compileStatementsInto(scope, text);
    }

    private Runnable compileExpression(String text){
        return () -> Compiler.compileExpression(scope, text);
    }

    private static void assertThrows(Class<? extends Exception> exception, Runnable r) {
        try {
            r.run();
        } catch (Exception e) {
            assertTrue(e.getMessage(), exception.isInstance(e));
            return;
        }
        assertTrue("Method did not throw.", false); // Fail the test
    }
}