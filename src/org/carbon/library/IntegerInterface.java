package org.carbon.library;

import org.carbon.CarbonInterface;

import java.util.Optional;

/**
 * TODO: Add refinements
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class IntegerInterface extends CarbonInterface {

    @Override
    public boolean isSupertypeOf(CarbonInterface carbonInterface) {
        if (carbonInterface instanceof IntegerInterface) {
            return true;
            // Later do more advanced checking (for range interfaces or steped ones)
        }
        return false;
    }

    @Override
    public Optional<CarbonInterface> getInterfaceMember(String name) {
        switch (name){
            case "<":
            case ">":
            case "<=":
            case ">=":
            case "==":
            case "!=":
                return Optional.of(new BooleanInterface());
            case "+":
            case "-":
            case "*":
            case "/":
                return Optional.of(new IntegerInterface());
            default:
                return super.getInterfaceMember(name);
        }
    }
}
