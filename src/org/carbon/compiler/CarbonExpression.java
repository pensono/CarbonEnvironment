package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.Optional;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class CarbonExpression implements PrettyPrintable, CarbonScope {
    private Optional<CarbonExpression> supertype;
    private CarbonScope scope;

    public CarbonExpression(CarbonScope scope, Optional<CarbonExpression> supertype){
        this.scope = scope;
        this.supertype = supertype;
    }

    public CarbonExpression(CarbonScope scope){
        this(scope, Optional.empty());
    }

    public CarbonExpression(CarbonScope scope, CarbonExpression supertype){
        this(scope, Optional.of(supertype));
    }

    /**
     * Returns true if this expression can only have one value
     * @return
     */
//    public abstract boolean isValue();

    public Optional<CarbonExpression> getSupertype() {
        return supertype;
    }

    public boolean isSubtypeOf(CarbonExpression expression) {
        if (supertype.isPresent()){
            return supertype.get() == expression || supertype.get().isSubtypeOf(expression);
        }
        return false;
    }

    /**
     * Returns a version of this expression with the given parameter filled out
     *
     * @param parameter
     * @return
     */
    public abstract CarbonExpression parameteritize(PrototypeExpression parameter);

    // The scope paramaeter may not be nececary
    public CarbonExpression reduce() { return this; }

    public Optional<CarbonExpression> getMember(String name) {
        return Optional.empty();
    }

    public CarbonScope getScope() {
        return scope;
    }
}
