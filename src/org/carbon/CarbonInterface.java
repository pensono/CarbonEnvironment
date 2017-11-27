package org.carbon;

import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * Represents a Unit interface
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class CarbonInterface extends CarbonExpression implements PrettyPrintable {
    public CarbonInterface(CarbonScope scope) {
        super(scope, null);
    }

    /**
     * Returns true if the given interface is equal to, or a supertype of this.
     * @param carbonInterface
     * @return
     */
    public boolean isSupertypeOf(CarbonInterface carbonInterface) {
        return (carbonInterface instanceof CarbonInterface);
    }

    public Optional<CarbonInterface> getInterfaceMember(String name) {
        return Optional.empty();
    }

    public String getShortString() {
        return this.getClass().getSimpleName();
    }

    @Override
    public Optional<CarbonExpression> getByIdentifier(String name) {
        return null;
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        return null;
    }
}
