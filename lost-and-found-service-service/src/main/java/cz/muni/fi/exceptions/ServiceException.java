package cz.muni.fi.exceptions;

import cz.muni.fi.service.ItemServiceImpl;
import org.springframework.dao.DataAccessException;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Exception used for problems with access to data on the service layer
 * @author Terezia Slaninakova (445526)
 */
public class ServiceException extends DataAccessException {
    private Logger logger = Logger.getLogger(ItemServiceImpl.class.getName());

    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
        logger.log(Level.SEVERE, msg, cause);

    }
}
