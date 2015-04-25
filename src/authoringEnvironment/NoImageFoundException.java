package authoringEnvironment;

public class NoImageFoundException extends Exception {

    private static final long serialVersionUID = 5680543765650334725L;

    public NoImageFoundException (String error) {
        super(error);
    }
}
