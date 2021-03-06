package uk.ac.tees.syntax.grammar.statement;

import uk.ac.tees.syntax.grammar.AbstractSyntaxTreeNode;
import uk.ac.tees.syntax.visitor.AbstractSyntaxTreeVisitor;

/**
 * RETURN statement, returns from subroutine to previous instruction.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public final class ReturnStatement extends Statement {

    public ReturnStatement() {
        super("RETURN");
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

        return object instanceof ReturnStatement;
    }
}