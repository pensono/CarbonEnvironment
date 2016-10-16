package org.carbon.compiler;

import org.carbon.PrettyPrintable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ethan Shea on 8/29/2016.
 */
public abstract class CarbonExpression implements PrettyPrintable {
    private CarbonExpression parent;
    private Optional<CarbonExpression> supertype;

    public CarbonExpression(CarbonExpression parent){
        this.parent = parent;
        this.supertype = Optional.empty();
    }

    public CarbonExpression(CarbonExpression parent, CarbonExpression supertype){
        this.parent = parent;
        this.supertype = Optional.of(supertype);
    }

    /**
     * Returns true if this expression can only have one value
     * @return
     */
//    public abstract boolean isValue();

    public Optional<CarbonExpression> getMember(String name) {
        return getParent().getMember(name);
    }

    public CarbonExpression getParent() {
        return parent;
    }

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
     * @param parameter
     * @return
     */
    public abstract CarbonExpression parameteritize(PrototypeExpression parameter);

    public CarbonExpression reduce() { return this; }
}
