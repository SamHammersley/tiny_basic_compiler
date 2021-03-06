package uk.ac.tees.syntax.grammar.factor;

import uk.ac.tees.syntax.grammar.AbstractSyntaxTreeNode;
import uk.ac.tees.syntax.grammar.expression.Expression;
import uk.ac.tees.syntax.visitor.AbstractSyntaxTreeVisitor;

import java.util.Objects;

/**
 * A factor that has a numeric value.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public final class NumberFactor implements Expression {

    private final int value;

    public NumberFactor(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Num(" + value + ")";
    }

    @Override
    public <T, K extends AbstractSyntaxTreeNode> void accept(AbstractSyntaxTreeVisitor<T, K> visitor) {
        visitor.visitNode(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NumberFactor)) {
            return false;
        }

        return value == ((NumberFactor) object).value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}