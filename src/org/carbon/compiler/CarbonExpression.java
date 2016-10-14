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

    public CarbonExpression(CarbonExpression parent, Optional<CarbonExpression> supertype){
        this.parent = parent;
        this.supertype = supertype;
    }

    /**
     * Returns true if this expression can only have one value
     * @return
     */
//    public abstract boolean isValue();

    public abstract Optional<CarbonExpression> getMember(String name);

    public CarbonExpression getParent() {
        return parent;
    }

    public Optional<CarbonExpression> getSupertype() {
        return supertype;
    }

    /**
     * Returns a version of this expression with the given parameter filled out
     * @param parameter
     * @return
     */
    public abstract CarbonExpression parameteritize(PrototypeExpression parameter);
}
