public class BalancedSystemIllegalArgumentException extends IllegalArgumentException {
    String message;
    public BalancedSystemIllegalArgumentException(String error) {
        this.message = error;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
