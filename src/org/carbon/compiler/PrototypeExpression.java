package org.carbon.compiler;

import org.carbon.CarbonExpression;
import org.carbon.CarbonScope;
import org.carbon.PrettyPrintable;

/**
 * Created by Ethan Shea on 8/31/2016.
 */
public abstract class PrototypeExpression implements PrettyPrintable {
    /**
     * Gives the identifier names (in the scope where this expression lives) of any dependencies
     * @return
     */
//    public abstract List<String> getDependencies();
    public abstract CarbonExpression link(CarbonScope scope);
}
