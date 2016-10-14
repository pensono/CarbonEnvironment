package org.carbon;

import com.google.common.base.Strings;

/**
 * @author Ethan
 */
public interface PrettyPrintable {
    default String getPrettyString() { return getPrettyString(0); }
    default String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getDebugString();
    }
    String getDebugString();

    static String indent(int levels){
        return Strings.repeat("  ", levels);
    }
}
