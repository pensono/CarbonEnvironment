package org.carbon.library;

import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.*;

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
    public boolean isSubtypeOf(CarbonInterface _interface){
        return (_interface.getClass() == IntegerInterface.class);
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
                return Optional.of(new BooleanInterface(getScope(), parameters));
            case "+":
            case "-":
            case "*":
            case "/":
                parameters = new ArrayList<>();
                parameters.add(new IntegerInterface(getScope()));
                return Optional.of(new IntegerInterface(getScope(), parameters));
            default:
                return super.getInterfaceMember(name);
        }
    }

    private static final Set<String> OPS = new HashSet<>(Arrays.asList("<", ">", "<=", ">=", "==", "!=", "+", "-", "*", "/"));
    @Override
    public boolean hasMember(String name) {
        return OPS.contains(name);
    }
}
