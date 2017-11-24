package org.carbon.library;

import org.carbon.*;

import static org.junit.Assert.*;

import org.carbon.Compiler;
import org.junit.Test;

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
        //expr.parameteritize()
    }

    @Test
    public void testAddition() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compile(scope, "5 + 4");

        assertEquals(((IntegerExpression) sum).getValue(), 9);
    }

    @Test
    public void testComparison() throws Exception {
        CarbonScope scope = new CarbonLibrary();
        CarbonExpression sum = Compiler.compile(scope, "5 > 4");

        assertEquals(((BooleanExpression) sum).getValue().get(), true);
    }

}