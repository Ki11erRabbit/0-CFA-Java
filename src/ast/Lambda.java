package ast;

public class Lambda implements ExpressionNode {
    private ExpressionNode parameter;
    private ExpressionNode body;

    public Lambda(ExpressionNode parameter, ExpressionNode body) {
        this.parameter = parameter;
        this.body = body;
    }

    public ExpressionNode getParameter() {
        return parameter;
    }

    public ExpressionNode getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "λ" + parameter.toString() + ". " + body.toString();
    }
}
