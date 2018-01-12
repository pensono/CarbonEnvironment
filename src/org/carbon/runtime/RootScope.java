package org.carbon.runtime;

import org.carbon.PrettyPrintable;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Ethan Shea on 9/6/2016.
 */
public class RootScope implements CarbonScope {
    private Map<String, CarbonExpression> members = new HashMap<>();

    @Override
    public String getBodyString(int level) {
        StringBuilder sb = new StringBuilder();
        sb.append(PrettyPrintable.indent(level));
        sb.append(getShortString());

        for (Map.Entry<String, CarbonExpression> entry : members.entrySet()){
            sb.append(PrettyPrintable.indent(level+1));
            sb.append(entry.getKey());
            sb.append("\n");
            sb.append(entry.getValue().getBodyString(level + 1));
        }
        return sb.toString();
    }

    public String getShortString() {
        return "Root expression";
    }

    @Override
    public Optional<CarbonExpression> getMember(String name) {
        return Optional.ofNullable(members.get(name));
    }

    public boolean hasMember(String name){
        return members.containsKey(name);
    }

    @Override
    public void addMember(String name, CarbonExpression member) {
        members.put(name, member);
    }
}
