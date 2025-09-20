package cfa.fact;

import java.util.Objects;

public class Fact {

    /*
    The Right hand side of a fact
     */
    private Value possibleValues;
    /*
    The Left hand side of a fact
     */
    private Substitution substitution;

    public Fact(Value possibleValues, Substitution substitution) {
        this.possibleValues = possibleValues;
        this.substitution = substitution;
    }

    public Value getValues() {
        return possibleValues;
    }

    public Substitution getSubstitution() {
        return substitution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fact fact = (Fact) o;
        return Objects.equals(possibleValues, fact.possibleValues) && Objects.equals(substitution, fact.substitution);
    }

    @Override
    public int hashCode() {
        return Objects.hash(possibleValues, substitution);
    }

    @Override
    public String toString() {
        return substitution.toString() + " ⊇ " + possibleValues.toString();
    }
}
