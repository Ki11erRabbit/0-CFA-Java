import ast.ExpressionNode;
import cfa.Cfa;
import parser.Lexer;
import parser.Parser;
import parser.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter a lambda calculus expression");
        String line = reader.readLine();

        Lexer lexer = new Lexer(line);
        List<Token> tokens = new ArrayList<>();
        Token token;
        while ((token = lexer.nextToken()) != null) {
            tokens.add(token);
        }

        Parser parser = new Parser(tokens);
        ExpressionNode node = parser.parse();

        Cfa cfa = new Cfa();
        cfa.loadPoints(node);
        cfa.initializeFacts(node);
        cfa.solve();

    }
}
