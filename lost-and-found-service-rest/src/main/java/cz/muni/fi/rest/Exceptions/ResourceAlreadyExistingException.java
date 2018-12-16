package cz.muni.fi.rest.Exceptions;
/**
 *
 * @author Augustin Nemec
 */
public class ResourceAlreadyExistingException extends RuntimeException {

    public ResourceAlreadyExistingException(String msg) {
        super(msg);
    }
}
