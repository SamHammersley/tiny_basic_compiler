package uk.ac.tees.syntax.parser.exception;

import uk.ac.tees.tokenizer.Token;

/**
 * Thrown when there is unrecognised command in the token sequence. This error message is documented in the Tiny BASIC
 * manual.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 * @see <a href="http://tinybasic.cyningstan.org.uk/page/12/tiny-basic-manual">Tiny BASIC Manual</a>
 */
public final class UnrecognisedCommandException extends ParseException {

    private static final String MESSAGE_TEMPLATE = "Unrecognised command on line %d, column %d: %s";

    public UnrecognisedCommandException(Token token) {
        super(String.format(MESSAGE_TEMPLATE, token.getRow(), token.getColumn(), token.toString()));
    }
}