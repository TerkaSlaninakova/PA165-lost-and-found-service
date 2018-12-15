package cz.muni.fi.service.exceptions;

import org.springframework.dao.DataAccessException;

/**
 * Exception used for problems with access to data on the service layer
 * @author Terezia Slaninakova (445526)
 */
public class ServiceException extends DataAccessException {

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
