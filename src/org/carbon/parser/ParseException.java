package org.carbon.parser;

import org.carbon.compiler.CarbonException;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public class ParseException extends CarbonException {
    public ParseException(String s) {
        super(s);
    }
}