package org.carbon;

import com.google.common.base.Strings;
import org.carbon.compiler.PrototypeExpression;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ethan
 */
public interface PrettyPrintable {
    static String prettyPrint(Set<? extends PrettyPrintable> children, int level) {
        return String.join("\n", children.stream().map(p -> p.getPrettyString(level)).collect(Collectors.toList()));
    }

    static String prettyPrint(Map<String, ? extends PrettyPrintable> children, int level) {
        return String.join("\n", children.entrySet().stream().map(
                e -> indent(level) + e.getKey() + " " + e.getValue().getShortString())
                .collect(Collectors.toList()));
    }

    default String getPrettyString() { return getPrettyString(0); }
    default String getPrettyString(int level) {
        return Strings.repeat("  ", level) + getShortString();
    }
    String getShortString();

    static String indent(int levels){
        return Strings.repeat("  ", levels);
    }
}
