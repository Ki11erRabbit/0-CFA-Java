package parser;

public class Token {
    public enum Type {
        LAMBDA, DOT, LPAREN, RPAREN, NUMBER, IDENTIFIER, PLUS
    }

    private Type type;
    private String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
