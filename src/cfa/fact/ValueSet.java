package cfa.fact;

import ast.ExpressionNode;

import java.util.HashSet;
import java.util.Objects;

public class ValueSet implements Value {
    private HashSet<ExpressionNode> set = new HashSet<>();

    public ValueSet(ExpressionNode node) {
        set.add(node);
    }

    public HashSet<ExpressionNode> getSet() {
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueSet valueSet = (ValueSet) o;
        return Objects.equals(set, valueSet.set);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(set);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{ ");
        int i = 0;
        for (ExpressionNode node : set) {
            builder.append(node.toString());
            if (i++ < set.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(" }");

        return builder.toString();
    }
}
