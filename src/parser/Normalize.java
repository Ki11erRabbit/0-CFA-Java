package parser;

import ast.*;

public class Normalize {

    public static ExpressionNode normalize(ExpressionNode node) {
        if (node instanceof Paren paren) {
            return paren.getExpression();
        } else if (node instanceof Application app) {
            return new Application(normalize(app.getFunction()), normalize(app.getArgument()));
        } else if (node instanceof Lambda lambda) {
            return new Lambda(lambda.getParameter(), normalize(lambda.getBody()));
        } else if (node instanceof BinaryOp bin) {
            return new BinaryOp(bin.getOperator(), normalize(bin.getLeft()), normalize(bin.getRight()));
        } else {
            return node;
        }
    }
}
