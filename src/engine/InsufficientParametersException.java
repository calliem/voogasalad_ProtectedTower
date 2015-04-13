package engine;

public class InsufficientParametersException extends Exception {

    private static final long serialVersionUID = 3081743524019968225L;

    public InsufficientParametersException (String string) {
        super(string);
    }

}
