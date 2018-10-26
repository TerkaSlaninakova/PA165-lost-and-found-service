package cz.muni.fi.exceptions;

/**
 * The base exception for the ItemEntity DAO layer.
 *
 * @author Augustin Nemec
 */
public class ItemDaoException extends Exception {

    /**
     * @see Exception#Exception(String, Throwable)
     */
    public ItemDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see Exception#Exception(String)
     */
    public ItemDaoException(String message) {
        super(message);
    }

}
