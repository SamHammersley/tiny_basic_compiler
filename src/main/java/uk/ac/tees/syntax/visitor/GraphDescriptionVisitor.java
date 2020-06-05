package uk.ac.tees.syntax.visitor;

import uk.ac.tees.syntax.grammar.AbstractSyntaxTreeNode;
import uk.ac.tees.syntax.grammar.Line;
import uk.ac.tees.syntax.grammar.Program;
import uk.ac.tees.syntax.grammar.UnassignedIdentifier;
import uk.ac.tees.syntax.grammar.expression.arithmetic.ArithmeticBinaryExpression;
import uk.ac.tees.syntax.grammar.expression.relational.RelationalBinaryExpression;
import uk.ac.tees.syntax.grammar.expression.factor.IdentifierFactor;
import uk.ac.tees.syntax.grammar.expression.factor.NumberFactor;
import uk.ac.tees.syntax.grammar.expression.factor.StringLiteral;
import uk.ac.tees.syntax.grammar.statement.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * An {@link AbstractSyntaxTreeNodeVisitor} that creates a DOT graph description for a given Abstract Syntax Tree. DOT
 * is a graph description language whereby described graphs can be visualised using Graphviz software.
 * <p>
 * {@see <a href="https://graphviz.gitlab.io/_pages/doc/info/lang.html">DOT Language</a>}
 * <p>
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public final class GraphDescriptionVisitor implements AbstractSyntaxTreeNodeVisitor<String> {

    /**
     * The first part of the dot graph description representing abstract syntax trees.
     */
    private static final String DOT_FILE_HEADER_FORMAT = "digraph %s {\n";

    /**
     * The last part of the dot graph description.
     */
    private static final String DOT_FILE_FOOTER = "}";

    /**
     * The string denoting that there is an edge between two given nodes.
     */
    private static final String DOT_FILE_ASSOCIATION = " -> ";

    /**
     * {@link StringBuilder} to build the graph description.
     */
    private final StringBuilder graphBuilder;

    /**
     * The file in which the DOT graph description is to be persisted.
     */
    private final String outputFile;

    /**
     * Constructs a {@link GraphDescriptionVisitor} with a given outputFile and graphName. If no outputFile is given,
     * the graph description produced will not be persisted in a file.
     *
     * @param outputFile the file to persist the graph description in, empty if persistence is not desired.
     * @param graphName  the name of the graph (typically the name of the program).
     */
    public GraphDescriptionVisitor(String outputFile, String graphName) {
        this.graphBuilder = new StringBuilder(String.format(DOT_FILE_HEADER_FORMAT, graphName));
        this.outputFile = outputFile;
    }

    /**
     * Constructs a {@link GraphDescriptionVisitor} with a graphName and no given outputFile, the graph description
     * produced will not be persisted in a file.
     *
     * @param graphName the name of the graph (typically the name of the program).
     */
    public GraphDescriptionVisitor(String graphName) {
        this("", graphName);
    }

    /**
     * Adds an association/edge between a parent node and a child node. Using the following syntax:
     *
     * <pre>{@code parent_node_id -> child_node_id}</pre>
     * <p>
     * In this case, {@link Object#hashCode()} is used to identify node objects.
     *
     * @param parent the parent node to connect the edge to.
     * @param child  the child node to connect the edge to.
     */
    private void associate(AbstractSyntaxTreeNode parent, AbstractSyntaxTreeNode child) {
        graphBuilder.append("\t").append(parent.hashCode()).append(DOT_FILE_ASSOCIATION).append(child.hashCode()).append("\n");
    }

    /**
     * Adds a graph node to the graph description, nodes are identifiable by their {@link Object#hashCode()}.
     *
     * @param node the node to add to the graph description.
     */
    private void addNode(AbstractSyntaxTreeNode node) {
        graphBuilder.append("\t").append(node.hashCode()).append(" [label=\"").append(node.toString()).append("\"]\n");
    }

    @Override
    public String visitTree(AbstractSyntaxTreeNode root) {
        root.accept(this);

        final String graphDescription = graphBuilder.append(DOT_FILE_FOOTER).toString();

        if (outputFile != null && !outputFile.isBlank()) {
            persist(graphDescription);
        }

        return graphDescription;
    }

    /**
     * Writes this graph description to a file with the .dot extension.
     *
     * @param graphDescription the DOT textual graph description.
     */
    private void persist(String graphDescription) {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(graphDescription);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void visit(UnassignedIdentifier node) {
        addNode(node);
    }

    @Override
    public void visit(IdentifierFactor node) {
        addNode(node);
    }

    @Override
    public void visit(NumberFactor node) {
        addNode(node);
    }

    @Override
    public void visit(StringLiteral node) {
        addNode(node);
    }

    @Override
    public void visit(ArithmeticBinaryExpression node) {
        addNode(node);
        associate(node, node.getLeft());
        associate(node, node.getRight());
    }

    @Override
    public void visit(RelationalBinaryExpression node) {
        addNode(node);
        associate(node, node.getLeft());
        associate(node, node.getRight());
    }

    @Override
    public void visit(ReturnStatement node) {
        addNode(node);
    }

    @Override
    public void visit(EndStatement node) {
        addNode(node);
    }

    @Override
    public void visit(Program root) {
        addNode(root);

        for (Line line : root.lines()) {
            associate(root, line);
        }
    }

    @Override
    public void visit(Line node) {
        addNode(node);
        associate(node, node.getStatement());
    }

    @Override
    public void visit(IfStatement node) {
        addNode(node);
        associate(node, node.getExpression());
        associate(node, node.getStatement());
    }

    @Override
    public void visit(GoSubStatement node) {
        addNode(node);
        associate(node, node.getExpression());
    }

    @Override
    public void visit(GoToStatement node) {
        addNode(node);
        associate(node, node.getExpression());
    }

    @Override
    public void visit(InputStatement node) {
        addNode(node);

        for (UnassignedIdentifier factor : node.getIdentifiers()) {
            associate(node, factor);
        }
    }

    @Override
    public void visit(LetStatement node) {
        addNode(node);

        associate(node, node.getIdentifier());
        associate(node, node.getValue());
    }

    @Override
    public void visit(PrintStatement node) {
        addNode(node);

        for (AbstractSyntaxTreeNode expression : node.getExpressions()) {
            associate(node, expression);
        }
    }

}