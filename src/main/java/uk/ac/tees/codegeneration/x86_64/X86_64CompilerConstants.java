package uk.ac.tees.codegeneration.x86_64;

import uk.ac.tees.syntax.grammar.expression.relational.RelationalOperator;

/**
 * Class holding constant data for X86-64 compiler.
 *
 * @author Sam Hammersley - Gonsalves (q5315908)
 */
final class X86_64CompilerConstants {

    /**
     * This field is used as a place holder in the builder for the local var address reservations. It's used to
     * find the index in the string to replace with the actual operations.
     */
    static final String LOCAL_VAR_RESERVE_PLACE_HOLDER = "_local_var_size_";
    static final int ASCII_DECIMAL_DIVISOR = 10;
    static final int ASCII_DIGIT_OFFSET = 48;
    static final int MAX_LOCAL_VARIABLE_COUNT = Math.abs('Z' - 'A') + 1;
    static final int SYS_WRITE_ID = 1;
    static final int STD_OUT_FILE_DESCRIPTOR = 1;
    static final int SYS_READ_ID = 0;
    static final int STD_IN_FILE_DESCRIPTOR = 0;
    static final int SYS_EXIT_ID = 60;
    static final String INDENTATION = "    ";
    static final String CALL_ASCII_CONVERSION = "call ascii_conversion";
    static final String CALL_ASCII_DECONVERSION = "call ascii_deconversion";

    /**
     * The registers used for system calls, as described in the Linux Application Binary Interface documentation.
     */
    private static final String[] SYS_CALL_PARAMETER_REGISTERS = {"rdi", "rsi", "rdx", "r10", "r8", "r9"};

    /**
     * Prevent instantiation.
     */
    private X86_64CompilerConstants() {

    }

    /**
     * Appends, to the given {@link StringBuilder}, the x86-64 NASM assembly for a system call.
     *
     * @param builder    the {@link StringBuilder} to append the systemcall assembly code to.
     * @param id         the system call identifier (dependant on operating system).
     * @param parameters the parameters used in the system call.
     */
    static void systemCall(StringBuilder builder, int id, Object... parameters) {
        builder.append(INDENTATION).append("mov rax, ").append(id).append('\n');

        for (int i = 0; i < parameters.length; i++) {
            String register = SYS_CALL_PARAMETER_REGISTERS[i];

            builder.append(INDENTATION).append("mov ").append(register).append(", ").append(parameters[i]).append('\n');
        }

        builder.append(INDENTATION).append("syscall\n");
    }

    /**
     * Gets the string value for conditional jump operations for the given {@link RelationalOperator}.
     *
     * @param operator the operator to get corresponding operation for.
     * @return the operation for the given operator as a string.
     */
    static String getJumpOperation(RelationalOperator operator) {
        String jumpOp = "j" + operator.name().charAt(0);

        int underscoreIndex = operator.name().indexOf('_');
        if (underscoreIndex != -1) {
            jumpOp += operator.name().charAt(underscoreIndex + 1);
        }

        return jumpOp.toLowerCase();
    }

    public enum DataSectionType {

        READ_ONLY(".rodata"),

        REGULAR(".data"),

        BSS(".bss");

        private final String label;

        DataSectionType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

    }

}