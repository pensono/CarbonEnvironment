package org.carbon;

import org.carbon.compiler.PrototypeExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a Unit interface
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class CarbonInterface extends CarbonExpression implements PrettyPrintable {
    private final List<CarbonInterface> parameters;

    public CarbonInterface(CarbonScope scope, List<CarbonInterface> parameters) {
        super(scope, null);
        this.parameters = parameters;
    }

    public CarbonInterface(CarbonScope scope) {
        this(scope, new ArrayList<>());
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
    public CarbonExpression parameteritize(CarbonExpression parameter) {
        return null;
    }

    public List<CarbonInterface> getParameters() {
        return this.parameters;
    }

    public int getArity() {
        return parameters.size();
    }
}
