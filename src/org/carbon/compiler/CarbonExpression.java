package org.carbon.compiler;

import com.google.common.base.Strings;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class CarbonExpression {
    public String getPrettyString() { return getPrettyString(0); }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString();
    }

    public abstract String getDebugString();
}
