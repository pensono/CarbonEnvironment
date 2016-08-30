package org.carbon.compiler;

import com.google.common.collect.PeekingIterator;

import java.util.Iterator;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class Parselet {
    public abstract CarbonExpression parse(PeekingIterator<String> tokens);
}
