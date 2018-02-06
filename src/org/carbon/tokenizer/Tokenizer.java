package org.carbon.tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public class Tokenizer {
    public static final String grammarChars = "\\{}.\\(\\)\\[\\],|:=;";

    public static List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        final String[] lines = input.split("\\n");
        for (int line = 0; line < lines.length; line++) {
            final int lineNo = line + 1; // For the closure. Line numbers start counting at 1
            tokens.addAll(Arrays.asList(lines[line].split("\\s+|(?=[\\W" + grammarChars + "])|(?<=[\\W" + grammarChars + "])")).stream()
                    .filter(s -> !s.isEmpty())
                    .map(s -> new Token(s, lines[lineNo - 1], lineNo, 0)) // TODO implement the character position instead of just 0
                    .collect(Collectors.toList()));
        }
        return tokens;
    }
}
