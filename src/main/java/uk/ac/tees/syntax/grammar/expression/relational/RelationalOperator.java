package uk.ac.tees.syntax.grammar.expression.relational;

import uk.ac.tees.syntax.grammar.expression.BinaryOperator;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Represents an relational operator for a boolean binary expression.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
public enum RelationalOperator implements BinaryOperator {

    LESS("<"),

    LESS_EQUAL("<="),

    EQUAL("="),

    NOT_EQUAL("<>"),

    GREATER(">"),

    GREATER_EQUAL(">=");

    /**
     * The symbol associated with this operator.
     */
    private final String symbol;

    RelationalOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the {@link RelationalOperator} associated with the specified symbol.
     *
     * @param symbol the symbol associated with the desired operator.
     * @return {@link RelationalOperator}
     */
    public static RelationalOperator fromSymbol(String symbol) {
        return Arrays
                .stream(values())
                .filter(o -> o.symbol.equals(symbol))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    public RelationalOperator negate() {
        switch (this) {
            case LESS:
                return GREATER_EQUAL;
            case LESS_EQUAL:
                return GREATER;

            case GREATER:
                return LESS_EQUAL;
            case GREATER_EQUAL:
                return LESS;

            case EQUAL:
                return NOT_EQUAL;
            case NOT_EQUAL:
                return EQUAL;

            default:
                throw new RuntimeException();
        }
    }

    @Override
    public String toString() {
        return name() + "(" + symbol + ")";
    }
}