package org.carbon.library;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.carbon.CarbonInterface;

import java.util.Optional;

/**
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class BooleanInterface extends CarbonInterface {
    private Optional<Boolean> refinement;

    public BooleanInterface(){
        refinement = Optional.empty();
    }

    public BooleanInterface(boolean refinement){
        this.refinement = Optional.of(Boolean.valueOf(refinement));
    }

    public boolean isSupertypeOf(CarbonInterface carbonInterface) {
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

    public Optional<CarbonInterface> getMember(String name) {
        return Optional.empty();
    }

    public String getShortString() {
        return "Boolean";
    }
}
