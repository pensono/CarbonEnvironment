package org.carbon;

import java.util.Optional;

/**
 * Represents a Unit interface
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class CarbonInterface implements PrettyPrintable {
    /**
     * Returns true if the given interface is equal to, or a supertype of this.
     * @param carbonInterface
     * @return
     */
    public boolean isSupertypeOf(CarbonInterface carbonInterface) {
        return (carbonInterface instanceof CarbonInterface);
    }

    public Optional<CarbonInterface> getMember(String name) {
        return Optional.empty();
    }

    public String getShortString() {
        return this.getClass().getSimpleName();
    }
}
