package org.carbon.parser;

import org.carbon.compiler.CarbonException;
import org.carbon.tokenizer.Token;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class ParseException extends CarbonException {
    public ParseException(String s) {
        super(s);
    }

    public ParseException(String s, Token token) {
        super(s); // TODO Meaningful output
    }
}
