package org.carbon.tokenizer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class TokenizerTest {
    @Test
    public void arithmeticWithNoSpaces() throws Exception {
        assertEquals(Tokenizer.tokenize("1+2"), "1", "+", "2");
    }

    @Test
    public void tokenizeNumberWithSymbol() throws Exception {
        assertEquals(Tokenizer.tokenize("[1]"), "[", "1", "]");
    }

    @Test
    public void ignoreLineComments() throws Exception {
        assertEquals(Tokenizer.tokenize("//This is a comment\n1+2\n"), "1", "+", "2");
        assertEquals(Tokenizer.tokenize("1+2//This is a comment\n"), "1", "+", "2");
        assertEquals(Tokenizer.tokenize("1+2\n//This is a comment"), "1", "+", "2");
    }

    public static void assertEquals(List<Token> tokens, String... expected){
        List<String> tokenText = tokens.stream().map(Token::getText).collect(Collectors.toList());
        Assert.assertEquals(tokenText, Arrays.asList(expected));
    }
}