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
        for (int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
            final int lineNo = lineIndex + 1; // For the closure. Line numbers start counting at 1
            final String line = stripLineComments(lines[lineIndex]);
            tokens.addAll(Arrays.asList(line.split("\\s+|(?=[\\W" + grammarChars + "])|(?<=[\\W" + grammarChars + "])")).stream()
                    .filter(s -> !s.isEmpty())
                    .map(s -> new Token(s, lines[lineNo - 1], lineNo, 0)) // TODO implement the character position instead of just 0
                    .collect(Collectors.toList()));
        }
        return tokens;
    }

    public static String stripLineComments(String line) {
        int end = line.indexOf("//");
        if (end == -1) {
            return line;
        } else {
            return line.substring(0, end);
        }
    }
}
