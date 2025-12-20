@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Map<String,String> handleAll(Exception ex){
        Map<String,String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}
