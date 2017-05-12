package org.carbon;

import com.google.common.base.Strings;

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
        return getShortString() + bodyWithReturn(this);
    }

    static String indent(int levels){
        return Strings.repeat("  ", levels);
    }

    static String prettyPrint(Set<? extends PrettyPrintable> children, int level) {
        return String.join("\n", children.stream().map(p -> p.getBodyString(level)).collect(Collectors.toList()));
    }

    static String prettyPrint(Map<String, ? extends PrettyPrintable> children, int level) {
        return String.join("\n", children.entrySet().stream().map(
                e -> {
                    return indent(level) + e.getKey() + " " + e.getValue().getShortString() +
                            bodyWithReturn(e.getValue(), level + 1);
                })
                .collect(Collectors.toList()));
    }

    static String bodyWithReturn(PrettyPrintable printable){
        return bodyWithReturn(printable, 1);
    }

    static String bodyWithReturn(PrettyPrintable printable, int level){
        String bodyString = printable.getBodyString(level);
        return bodyString.isEmpty() ? "" : "\n " + bodyString;
    }
}
