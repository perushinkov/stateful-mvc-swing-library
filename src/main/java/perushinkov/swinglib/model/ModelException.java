package perushinkov.swinglib.model;

/**
 * Used for transforming stacktraces resulting from exceptions in model
 * to user-readable stacktraces.
 * @author eglavchev
 */
public class ModelException extends Exception {
  private static final long serialVersionUID = 1L;
  
  /**
   * Forms a new exception with the given msg, but appends to this msg,
   * the message of the given exception. Recursive calls for a sort of
   * user-friendly stacktrace
   * @param ex
   * @param msg
   */
  public ModelException(Exception ex, String msg) {
    super(msg + "\r\n" + ex.getMessage());
  }
}
