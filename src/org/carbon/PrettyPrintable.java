package org.carbon;

import com.google.common.base.Strings;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public interface PrettyPrintable {
    String getShortString();
    default String getBodyString() { return getBodyString(0); }
    //    default String getBodyString(int level) {
//        return Strings.repeat("  ", level) + getShortString();
//    }
    default String getBodyString(int level) {
        return "";
    }

    default String getFullString() {
        return getFullString(0);
    }

    default String getFullString(int level) {
        return indent(level) + getShortString() + bodyWithReturn(this, level + 1);
    }

    static String indent(int levels){
        return Strings.repeat("  ", levels);
    }

    static String bodyString(Set<? extends PrettyPrintable> children, int level) {
        return bodyString(new HashSet<>(children), level);
    }

    static String bodyString(List<? extends PrettyPrintable> children, int level) {
        return String.join("\n", children.stream().map(p -> p.getFullString(level)).collect(Collectors.toList()));
    }

    static String bodyString(Map<String, ? extends PrettyPrintable> children, int level) {
        return children.entrySet().stream().map(
                e -> {
                    return indent(level) + e.getKey() + " = " + e.getValue().getShortString() +
                            bodyWithReturn(e.getValue(), level + 1);
                })
                .collect(Collectors.joining("\n"));
    }

    static String bodyWithReturn(PrettyPrintable printable){
        return bodyWithReturn(printable, 1);
    }

    static String bodyWithReturn(PrettyPrintable printable, int level){
        String bodyString = printable.getBodyString(level);
        return bodyString.isEmpty() ? "" : "\n" + bodyString;
    }
}
