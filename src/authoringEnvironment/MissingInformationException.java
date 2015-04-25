package authoringEnvironment;

public class MissingInformationException extends Exception {

    private static final long serialVersionUID = -4774460612320034049L;

    public MissingInformationException (String errorMessage) {
        super(errorMessage);
    }

}
