package cfa.fact;

import java.util.Objects;

public class SubstitutionApply implements Substitution {
    private SubstitutionSimple value;
    private SubstitutionSimple result;

    public SubstitutionApply(SubstitutionSimple value, SubstitutionSimple result) {
        this.value = value;
        this.result = result;
    }

    public SubstitutionSimple getValue() {
        return value;
    }

    public SubstitutionSimple getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubstitutionApply that = (SubstitutionApply) o;
        return Objects.equals(value, that.value) && Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, result);
    }

    @Override
    public String toString() {
        return value.toString() + " ↦ " + result.toString();
    }
}
