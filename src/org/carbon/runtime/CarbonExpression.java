package org.carbon.runtime;

import org.carbon.PrettyPrintable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class CarbonExpression implements PrettyPrintable, CarbonScope {
    private CarbonInterface _interface;
    private CarbonScope scope;

    public CarbonExpression(CarbonScope scope, CarbonInterface _interface){
        this.scope = scope;
        this._interface = _interface;
    }

    /**
     * Returns true if this expression can only have one value
     * @return
     */
//    public abstract boolean isValue();

    public CarbonInterface getInterface() {
        return _interface;
    }

    /**
     * Returns a version of this expression with the given parameter filled out.
     *
     * This parameter must typecheck.
     *
     * @param parameter
     * @return
     */
    public abstract CarbonExpression apply(CarbonExpression parameter);

    // The scope parameter may not be necessary
    public CarbonExpression reduce() { return this; }

    public Optional<CarbonExpression> getMember(String name) {
        return Optional.empty();
    }

    public void addMember(String name, CarbonExpression expression) {
        throw new UnsupportedOperationException(); // Ewww gross...
    }

    public Optional<CarbonExpression> getByIdentifier(List<String> identifier) {
        if (identifier.isEmpty()) {
            return Optional.of(this);
        } else {
            if (hasMember(identifier.get(0))) {
                String name = identifier.remove(0);
                return getMember(name).get().getByIdentifier(identifier);
            } else {
                return getScope().getByIdentifier(identifier);
            }
        }
    }

    @Override
    public boolean hasMember(String name) {
        return getInterface().hasMember(name);
    }

    public CarbonScope getScope() {
        return scope;
    }
}
