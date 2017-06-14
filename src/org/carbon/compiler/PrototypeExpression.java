package org.carbon.compiler;

import com.google.common.base.Strings;
import org.carbon.PrettyPrintable;

import java.util.List;
import java.util.Set;

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
