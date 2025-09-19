package ast;

public class Application implements ExpressionNode {
    private ExpressionNode function;
    private ExpressionNode argument;

    public Application(ExpressionNode function, ExpressionNode argument) {
        this.function = function;
        this.argument = argument;
    }

    public ExpressionNode getFunction() {
        return function;
    }

    public ExpressionNode getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return function.toString() + " " + argument.toString();
    }
}
