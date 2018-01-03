package org.carbon.library;

import org.carbon.CarbonInterface;
import org.carbon.CarbonScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO: Add refinements
 * @author Ethan Shea
 * @date 11/24/2017
 */
public class IntegerInterface extends CarbonInterface {
    public IntegerInterface(CarbonScope scope) {
        super(scope);
    }

    public IntegerInterface(CarbonScope scope, List<CarbonInterface> parameters) {
        super(scope, parameters);
    }

    @Override
    public boolean isSupertypeOf(CarbonInterface _interface){
        return isSupertypeOfUnparameterized(_interface);
        // Later do more advanced checking (for range interfaces or stepped ones)
    }

    public static boolean isSupertypeOfUnparameterized(CarbonInterface _interface) {
        if (_interface instanceof IntegerInterface) {
            return true;
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
                List<CarbonInterface> parameters = new ArrayList<>();
                parameters.add(new IntegerInterface(getScope()));
                parameters.add(new IntegerInterface(getScope()));
                return Optional.of(new BooleanInterface(getScope(), parameters));
            case "+":
            case "-":
            case "*":
            case "/":
                parameters = new ArrayList<>();
                parameters.add(new IntegerInterface(getScope()));
                parameters.add(new IntegerInterface(getScope()));
                return Optional.of(new IntegerInterface(getScope(), parameters));
            default:
                return super.getInterfaceMember(name);
        }
    }
}
