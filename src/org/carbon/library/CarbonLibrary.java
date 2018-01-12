package org.carbon.library;

import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.RootScope;

/**
 * Created by Ethan Shea on 6/19/2017.
 */
public class CarbonLibrary extends RootScope {
    public CarbonLibrary(){
        addMember("Interface", new CarbonInterface(this));
        addMember("Boolean", new BooleanInterface(this));
        addMember("True", new BooleanExpression(this, true));
        addMember("False", new BooleanExpression(this, false));
        addMember("Integer", new IntegerInterface(this));
    }
}
