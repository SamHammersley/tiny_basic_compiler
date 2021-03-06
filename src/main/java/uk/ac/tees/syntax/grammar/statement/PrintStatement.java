package uk.ac.tees.syntax.grammar.statement;

import uk.ac.tees.syntax.grammar.AbstractSyntaxTreeNode;
import uk.ac.tees.syntax.visitor.AbstractSyntaxTreeVisitor;

import java.util.Objects;

/**
 * PRINT statement prints the expression to standard out, followed by a new line.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public class PrintStatement extends Statement {

    /**
     * The {@link AbstractSyntaxTreeNode} expression to print.
     */
    private final AbstractSyntaxTreeNode expression;

    public PrintStatement(AbstractSyntaxTreeNode expression) {
        super("PRINT");
        this.expression = expression;
    }

    public AbstractSyntaxTreeNode getExpression() {
        return expression;
    }

    @Override
    public <T, K extends AbstractSyntaxTreeNode> void accept(AbstractSyntaxTreeVisitor<T, K> visitor) {
        expression.accept(visitor);

        visitor.visitNode(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof PrintStatement)) {
            return false;
        }

        PrintStatement other = (PrintStatement) object;

        return expression.equals(other.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }
}