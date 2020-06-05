package uk.ac.tees.syntax.grammar.statement;

import uk.ac.tees.syntax.grammar.AbstractSyntaxTreeNode;
import uk.ac.tees.syntax.visitor.AbstractSyntaxTreeNodeVisitor;

import java.util.List;
import java.util.Objects;

/**
 * PRINT statement prints each of the expressions to standard out, seperated by \n.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public final class PrintStatement extends Statement {

    /**
     * {@link List} of expressions to print.
     */
    private final List<AbstractSyntaxTreeNode> expressions;

    public PrintStatement(List<AbstractSyntaxTreeNode> expressions) {
        super("PRINT");
        this.expressions = expressions;
    }

    public List<AbstractSyntaxTreeNode> getExpressions() {
        return expressions;
    }

    @Override
    public void accept(AbstractSyntaxTreeNodeVisitor visitor) {
        expressions.forEach(e -> e.accept(visitor));

        visitor.visit(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), expressions);
    }

}