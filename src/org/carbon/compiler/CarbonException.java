package org.carbon.compiler;

/**
 * Created by Ethan Shea on 3/19/2017.
 */
public abstract class CarbonException extends RuntimeException {
    public CarbonException(String s){
        super(s);
    }
}
