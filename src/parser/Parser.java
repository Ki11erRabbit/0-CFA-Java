package parser;

import ast.*;

import java.util.List;

public class Parser {

    private List<Token> tokens;
    private int position;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    private ExpressionNode parseApp() {
        if (tokens.get(position).getType() == Token.Type.LAMBDA) {
            ExpressionNode lambda = parseLambda();
            if (position < tokens.size()) {
                ExpressionNode arg = parseLambda();

                return new Application(lambda, arg);
            }
            return lambda;
        } else if (tokens.get(position).getType() == Token.Type.LPAREN) {
            position++;
            ExpressionNode expr = parseApp();
            if (tokens.get(position).getType() != Token.Type.RPAREN) {
                throw new RuntimeException("Expected closing paren");
            }
            position++;
            expr = new Paren(expr);
            if (position < tokens.size()) {
                ExpressionNode arg = parseLambda();

                return new Application(expr, arg);
            }
            return expr;
        } else {
            return parseLambda();
        }
    }

    private ExpressionNode parseLambda() {
        if (tokens.get(position).getType() == Token.Type.LAMBDA) {
            position++;
            ExpressionNode arg = parseIdentifier();
            if (tokens.get(position).getType() != Token.Type.DOT) {
                throw new RuntimeException("Expected dot in lambda expression");
            }
            position++;
            ExpressionNode body = parseExpr();

            return new Lambda(arg, body);
        } else {
            return parseExpr();
        }
    }

    private ExpressionNode parseExpr() {
        Token token = tokens.get(position);
        position++;
        return switch (token.getType()) {
            case IDENTIFIER -> {
                ExpressionNode left = new ast.Variable(token.getValue());
                if (position < tokens.size() && tokens.get(position).getType() == Token.Type.PLUS) {
                    position++;
                    ExpressionNode right = parseExpr();
                    yield new ast.BinaryOp(BinaryOp.BinaryOperator.ADD, left, right);
                } else {
                    yield left;
                }
            }
            case LPAREN -> {
                ExpressionNode expr = parseExpr();
                Token rparenToken = tokens.get(position);
                if (rparenToken.getType() != Token.Type.RPAREN) {
                    throw new RuntimeException("Expected ')' after expression");
                }
                position++;
                yield new Paren(expr);
            }
            case NUMBER -> {
                ExpressionNode left = new ast.Number(Integer.parseInt(token.getValue()));
                if (position < tokens.size() && tokens.get(position).getType() == Token.Type.PLUS) {
                    position++;
                    ExpressionNode right = parseExpr();
                    yield new ast.BinaryOp(BinaryOp.BinaryOperator.ADD, left, right);
                } else {
                    yield left;
                }
            }
            default -> throw new RuntimeException("Unexpected token: " + token.getValue());
        };
    }

    public ExpressionNode parse() {
        return parseApp();
    }

    private ExpressionNode parseIdentifier() {
        Token token = tokens.get(position);
        return switch (token.getType()) {
            case IDENTIFIER -> {
                position++;
                yield new ast.Variable(token.getValue());
            }
            default -> throw new RuntimeException("Unexpected token: " + token.getValue());
        };
    }
}
