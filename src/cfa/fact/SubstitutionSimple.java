package cfa.fact;

import utils.SubscriptPrinter;

import java.util.Objects;

public class SubstitutionSimple implements Substitution {
    private int index;

    public SubstitutionSimple(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubstitutionSimple that = (SubstitutionSimple) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(index);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("α");
        builder.append(SubscriptPrinter.subscript(index));
        return builder.toString();
    }
}
