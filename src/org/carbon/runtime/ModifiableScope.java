package org.carbon.runtime;

/**
 * @author Ethan Shea
 * @date 2/7/2018
 */
public interface ModifiableScope extends CarbonScope {
    void addMember(String name, CarbonExpression member); // I don't like that this method exists
}
