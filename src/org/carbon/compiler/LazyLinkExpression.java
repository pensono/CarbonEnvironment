package org.carbon.compiler;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Ethan
 */
public class LazyLinkExpression extends CarbonExpression {
    private CarbonExpression linkedExpression;
    private Function<CarbonExpression, CarbonExpression> linker;

    public LazyLinkExpression(Function<CarbonExpression, CarbonExpression> linker, CarbonExpression parent){
        super(parent);
        this.linker = linker;
    }

    public Optional<CarbonExpression> getMember(String name) {
        return getParent().getMember(name);
    }

    public CarbonExpression getParent() {
        return super.getParent();
    }

    public Optional<CarbonExpression> getSupertype() {
        return getExpression().getSupertype();
    }

    public boolean isSubtypeOf(CarbonExpression expression) {
        return getExpression().isSubtypeOf(expression);
    }

    public CarbonExpression parameteritize(PrototypeExpression parameter){
        return getExpression().parameteritize(parameter);
    }

    public CarbonExpression reduce() { return getExpression(); }

    private CarbonExpression getExpression(){
        if (linkedExpression == null){
            linkedExpression = linker.apply(getParent());
        }
        return linkedExpression;
    }

    @Override
    public String getShortString() {
        return "LazyLink expression";
    }
}
