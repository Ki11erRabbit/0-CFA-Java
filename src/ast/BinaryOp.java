package ast;

public class BinaryOp implements ExpressionNode {
    public enum BinaryOperator {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private BinaryOperator operator;
    private ExpressionNode left;
    private ExpressionNode right;

    public BinaryOp(BinaryOperator operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    public BinaryOperator getOperator() {
        return operator;
    }
    public ExpressionNode getLeft() {
        return left;
    }
    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(left.toString());
        switch (operator) {
            case ADD:
                builder.append(" + ");
                break;
            case SUBTRACT:
                builder.append(" - ");
                break;
            case MULTIPLY:
                builder.append(" * ");
                break;
            case DIVIDE:
                builder.append(" / ");
                break;
        }
        builder.append(right.toString());
        return builder.toString();
    }
}
