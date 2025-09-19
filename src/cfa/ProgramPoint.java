package cfa;

import ast.ExpressionNode;

public class ProgramPoint {

    public enum Type {
        POINT,
        VALUE,
    }

    private Type type;
    private int index;
    private ExpressionNode node;

    public ProgramPoint(Type type, int index, ExpressionNode node) {
        this.type = type;
        this.index = index;
        this.node = node;
    }

    public Type getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public ExpressionNode getNode() {
        return node;
    }
}
