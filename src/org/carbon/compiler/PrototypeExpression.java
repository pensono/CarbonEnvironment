package org.carbon.compiler;

import com.google.common.base.Strings;
import org.carbon.PrettyPrintable;

import java.util.Set;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public abstract class PrototypeExpression implements PrettyPrintable {
    public abstract CarbonExpression link(CarbonExpression scope);
}
