public class Tests {

    public static void main(String[] args) {
        runTest("λx.x 3");

        runTest("(λx.x λy.y) 3");

        runTest("(λx. (λy.x) 3) 3)");
    }

    private static void runTest(String line) {
        System.out.println(line + ": ");
        Main.run(line);
    }
}
