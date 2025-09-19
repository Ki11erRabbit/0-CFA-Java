package parser;

public class Lexer {

    private String input;
    private int position;


    public Lexer(String input) {
        this.input = input;
        this.position = 0;
    }

    public Token nextToken() {
        while (position < input.length()) {
            char currentChar = input.charAt(position);

            switch (currentChar) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    position++;
                    continue;
                case '\\':
                case 'λ':
                    position++;
                    return new Token(Token.Type.LAMBDA, "λ");
                case '.':
                    position++;
                    return new Token(Token.Type.DOT, ".");
                case '(':
                    position++;
                    return new Token(Token.Type.LPAREN, "(");
                case ')':
                    position++;
                    return new Token(Token.Type.RPAREN, ")");
                case '+':
                    position++;
                    return new Token(Token.Type.PLUS, "+");
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    int start = position;
                    while (position < input.length() && Character.isDigit(input.charAt(position))) {
                        position++;
                    }
                    String number = input.substring(start, position);
                    return new Token(Token.Type.NUMBER, number);
                default:
                    if (Character.isLetter(currentChar)) {
                        start = position;
                        while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) {
                            position++;
                        }
                        String identifier = input.substring(start, position);
                        return new Token(Token.Type.IDENTIFIER, identifier);
                    } else {
                        throw new RuntimeException("Unexpected character: " + currentChar);
                    }
            }
        }

        return null; // End of input
    }
}
