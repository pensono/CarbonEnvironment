package org.carbon;

import java.util.Optional;

/**
 * Should this just be called Scope?
 * Created by Ethan Shea on 6/13/2017.
 */
public interface CarbonScope extends PrettyPrintable {
    Optional<CarbonExpression> getByIdentifier(String name);
}
