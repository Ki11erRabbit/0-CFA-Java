package cfa;

public class Produces implements Subpart {
    private Alpha source;
    private Alpha result;

    public Produces(Alpha source, Alpha result) {
        this.source = source;
        this.result = result;
    }

    public Alpha getSource() {
        return source;
    }

    public Alpha getResult() {
        return result;
    }

}
