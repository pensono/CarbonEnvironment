package org.carbon.tokenizer;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Ethan
 */
public class TokenizerTest {
    @Test
    public void arithmeticWithNoSpaces() throws Exception {
        assertSame(Tokenizer.tokenize("1+2"), "1", "+", "2");
    }

    @Test
    public void tokenizeNumberWithSymbol() throws Exception {
        assertSame(Tokenizer.tokenize("[1]"), "[", "1", "]");
    }

    public static void assertSame(List<Token> tokens, String... expected){
        assertEquals("Incorrect number of tokens parsed.", tokens.size(), expected.length);

        for (int i = 0; i < expected.length; i++) {
            assertEquals(tokens.get(i).getText(), expected[i]);
        }
    }
}