package org.carbon.compiler;

import com.google.common.base.Strings;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public abstract class PrototypeExpression {
    public String getPrettyString() { return getPrettyString(0); }

    public String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString();
    }

    public abstract String getDebugString();
}
