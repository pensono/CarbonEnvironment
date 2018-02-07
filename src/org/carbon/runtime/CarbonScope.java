package org.carbon.runtime;

import org.carbon.PrettyPrintable;

import java.util.List;
import java.util.Optional;
import java.util.Stack;

/**
 * Should this just be called Scope?
 * Created by Ethan Shea on 6/13/2017.
 */
public interface CarbonScope extends PrettyPrintable {
    Optional<CarbonExpression> getMember(String name);

    /**
     * If this returns true, then getMember must return something
     * @param name
     * @return
     */
    boolean hasMember(String name);

    default Optional<CarbonExpression> getByIdentifier(List<String> identifier) {
        if (identifier.isEmpty()) {
            return Optional.empty();
        } else {
            String firstName = identifier.remove(0); // Mutation!
            if (hasMember(firstName)) {
                return getMember(firstName).get().getByIdentifier(identifier);
            } else {
                return Optional.empty();
            }
        }
    }
}
