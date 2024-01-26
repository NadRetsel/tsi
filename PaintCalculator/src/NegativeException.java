package src;
public class NegativeException extends Exception{

    public NegativeException(){}
    public NegativeException(String errorMessage) {
        super(errorMessage);
    }
}
