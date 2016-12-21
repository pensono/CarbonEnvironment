package org.carbon;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.ParseException;
import org.carbon.compiler.PrototypeExpression;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ethan Shea on 9/6/2016.
 */
public class RootExpression extends CarbonExpression {
    private Map<String, CarbonExpression> members = new HashMap<>();

    public RootExpression() {
        super(null); // What to send in as the parent of this??
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(members.get(name));
    }

    @Override
    public CarbonExpression parameteritize(PrototypeExpression parameter) {
        throw new ParseException("Cannot parameterize root. (How the hell did you even do this?!)");
    }

    @Override
    public String getPrettyString(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(PrettyPrintable.indent(level));
        sb.append(getShortString());

        for (Map.Entry<String, CarbonExpression> entry : members.entrySet()){
            sb.append(PrettyPrintable.indent(level+1));
            sb.append(entry.getKey());
            sb.append("\n");
            sb.append(entry.getValue().getPrettyString(level + 1));
        }
        return sb.toString();
    }

    public String getShortString() {
        return "Root expression";
    }

    public void putMember(String name, CarbonExpression expression){
        members.put(name, expression);
    }
}
