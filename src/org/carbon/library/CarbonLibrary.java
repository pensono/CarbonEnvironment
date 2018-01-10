package org.carbon.library;

import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.RootScope;

/**
 * Created by Ethan Shea on 6/19/2017.
 */
public class CarbonLibrary extends RootScope {
    public CarbonLibrary(){
        putMember("Interface", new CarbonInterface(this));
        putMember("Boolean", new BooleanInterface(this));
        putMember("True", new BooleanExpression(this, true));
        putMember("False", new BooleanExpression(this, false));
        putMember("Integer", new IntegerInterface(this));
    }
}
