package am.aua.gameoflife.exceptions;

public class PatternFormatException extends Exception{
    public PatternFormatException(String message){
        super(message);
    }
    public PatternFormatException(){
        super("Invalid Pattern format");
    }
}
