package ast;

public class Paren implements ExpressionNode {
    private ExpressionNode expression;

    public Paren(ExpressionNode expression) {
        this.expression = expression;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }
}
