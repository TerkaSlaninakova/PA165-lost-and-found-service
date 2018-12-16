package cz.muni.fi.rest.Exceptions;
/**
 *
 * @author Augustin Nemec
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
