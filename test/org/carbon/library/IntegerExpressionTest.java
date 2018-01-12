package org.carbon.library;

import static org.junit.Assert.*;

import org.carbon.compiler.Compiler;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;
import org.carbon.runtime.RootScope;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;
/**
 * Created by Ethan Shea on 6/19/2017.
 */
public class IntegerExpressionTest {
    // TODO make this also test subclasses
    @Test
    public void hasNoMembers() throws Exception {
        RootScope scope = new RootScope();
        CarbonExpression expr = new IntegerExpression(scope, 0);

        // TODO add mockito and test for any string
        assertEquals(expr.getMember("notAMember"), Optional.empty());
    }

    @Test
    public void parameteritize() throws Exception {
        RootScope scope = new RootScope();
        CarbonExpression expr = new IntegerExpression(scope, 0);

        CarbonExpression integer = new IntegerExpression(scope, 0);
        //expr.apply()
    }

    @Test
    public void testAddition() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "5 + 4");

        assertEquals(((IntegerExpression) sum).getValue(), 9);
    }

    @Test
    public void testComparison() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compileExpression(scope, "5 > 4");

        assertEquals(((BooleanExpression) sum).getValue().get(), true);
    }

    @Test
    public void isSubtypeOfUnparameterized() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression integer = new IntegerExpression(scope, Mockito.anyInt());

        assertTrue(IntegerInterface.isSupertypeOfUnparameterized(integer.getInterface()));
    }

}