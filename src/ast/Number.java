package ast;

public class Number implements ExpressionNode {
    private int value;

    public Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
