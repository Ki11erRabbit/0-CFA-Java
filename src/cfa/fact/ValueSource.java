package cfa.fact;

import utils.SubscriptPrinter;

import java.util.Objects;

public class ValueSource implements Value{
    int index;

    public ValueSource(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueSource that = (ValueSource) o;
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
