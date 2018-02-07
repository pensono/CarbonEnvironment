package org.carbon.parser;

import org.carbon.compiler.TypeException;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.CarbonInterface;
import org.carbon.runtime.CarbonScope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ethan
 */
public class TypeNode {
    private IdentifierNode identifier;
    private List<RefinementNode> refinements;

    public TypeNode(IdentifierNode identifier) {
        this(identifier, new ArrayList<>());
    }

    public TypeNode(IdentifierNode identifier, List<RefinementNode> refinements) {
        this.identifier = identifier;
        this.refinements = refinements;
    }

    public CarbonInterface linkInterface(CarbonScope scope) {
        CarbonExpression expr = identifier.linkExpression(scope);
        if (!(expr instanceof CarbonInterface)) { // This probably won't work
            throw new TypeException(expr.getShortString() + " is not an interface");
        }

        CarbonInterface carbonInterface = (CarbonInterface) expr;

        for (RefinementNode refinementNode : refinements) {
            carbonInterface = refinementNode.linkRefinement(scope, carbonInterface);
        }

        return carbonInterface;
    }

    public IdentifierNode getIdentifier() {
        return identifier;
    }

    public List<RefinementNode> getRefinements() {
        return refinements;
    }
}
