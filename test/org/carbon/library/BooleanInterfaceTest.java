package org.carbon.library;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class BooleanInterfaceTest {
    @Test
    public void isSupertypeOfCorrect() throws Exception {
        BooleanInterface noRefinement = new BooleanInterface();

        assertTrue(noRefinement.isSupertypeOf(new BooleanInterface(Mockito.anyBoolean())));
        assertTrue(noRefinement.isSupertypeOf(new BooleanInterface()));

        BooleanInterface aRefinement = new BooleanInterface(true);

        assertTrue(aRefinement.isSupertypeOf(new BooleanInterface(true)));
        assertFalse(aRefinement.isSupertypeOf(new BooleanInterface(false)));
        assertFalse(aRefinement.isSupertypeOf(new BooleanInterface()));
    }

    @Test
    public void getShortString() throws Exception {
        assertEquals(new BooleanInterface().getShortString(), "Boolean");
    }

}