package org.carbon.library;

import org.carbon.runtime.CarbonScope;
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
        CarbonScope lib = new CarbonLibrary();

        BooleanInterface noRefinement = new BooleanInterface(lib);

        assertTrue(noRefinement.isSubtypeOf(new BooleanInterface(lib, Mockito.anyBoolean())));
        assertTrue(noRefinement.isSubtypeOf(new BooleanInterface(lib)));

        BooleanInterface aRefinement = new BooleanInterface(lib, true);

        assertTrue(aRefinement.isSubtypeOf(new BooleanInterface(lib, true)));
        assertFalse(aRefinement.isSubtypeOf(new BooleanInterface(lib, false)));
        assertFalse(aRefinement.isSubtypeOf(new BooleanInterface(lib)));
    }

    @Test
    public void getShortString() throws Exception {
        assertEquals(new BooleanInterface(new CarbonLibrary()).getShortString(), "Boolean");
    }

}