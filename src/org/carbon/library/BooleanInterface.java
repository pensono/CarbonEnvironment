package org.carbon.library;

import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.List;
import java.util.Optional;

/**
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class BooleanInterface extends CarbonInterface {
    private Optional<Boolean> refinement;

    public BooleanInterface(CarbonScope scope) {
        super(scope);
        refinement = Optional.empty();
    }

    public BooleanInterface(CarbonScope scope, boolean refinement){
        super(scope);
        this.refinement = Optional.of(refinement);
    }

    public BooleanInterface(CarbonScope scope, List<CarbonInterface> parameters) {
        super(scope, parameters);
        refinement = Optional.empty();
    }

    public BooleanInterface(CarbonScope scope, List<CarbonInterface> parameters, boolean refinement) {
        super(scope, parameters);
        this.refinement = Optional.of(refinement);
    }

    public boolean isSubtypeOf(CarbonInterface carbonInterface) {
        if (carbonInterface instanceof BooleanInterface){
            if (refinement.isPresent()){
                BooleanInterface booleanInterface = (BooleanInterface) carbonInterface;
                if (booleanInterface.refinement.isPresent()){
                    return refinement.get().equals(booleanInterface.refinement.get());
                }
                return false;
            }
            return true;
        }
        return false;
    }

    public Optional<CarbonInterface> getInterfaceMember(String name) {
        return Optional.empty(); // For now
    }

    public String getShortString() {
        return "Boolean";
    }
}
