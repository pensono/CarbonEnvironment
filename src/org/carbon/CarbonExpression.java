package org.carbon;

import org.carbon.compiler.PrototypeExpression;

import java.util.Optional;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class CarbonExpression implements PrettyPrintable, CarbonScope {
    private CarbonInterface supertype;
    private CarbonScope scope;

    public CarbonExpression(CarbonScope scope, CarbonInterface supertype){
        this.scope = scope;
        this.supertype = supertype;
    }

    /**
     * Returns true if this expression can only have one value
     * @return
     */
//    public abstract boolean isValue();

    public CarbonInterface getInterface() {
        return supertype;
    }

    /**
     * Returns a version of this expression with the given parameter filled out
     *
     * @param parameter
     * @return
     */
    public abstract CarbonExpression parameteritize(PrototypeExpression parameter);

    // The scope parameter may not be necessary
    public CarbonExpression reduce() { return this; }

    public Optional<CarbonExpression> getMember(String name) {
        return Optional.empty();
    }

    public CarbonScope getScope() {
        return scope;
    }
}
