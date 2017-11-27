package org.carbon;

import org.carbon.library.BooleanExpression;
import org.carbon.library.BooleanInterface;
import org.carbon.library.IntegerExpression;
import org.carbon.library.IntegerInterface;

import java.util.Optional;

/**
 * Created by Ethan Shea on 6/19/2017.
 */
public class CarbonLibrary extends RootScope {
    public CarbonLibrary(){
        putMember("Interface", new CarbonInterface(this));
        putMember("Boolean", new BooleanInterface());
        putMember("True", new BooleanExpression(this, true));
        putMember("False", new BooleanExpression(this, false));
        putMember("Integer", new IntegerInterface());
    }
}
