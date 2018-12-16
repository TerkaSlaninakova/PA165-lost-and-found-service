package cz.muni.fi.service.exceptions;

import cz.muni.fi.service.ItemService;
import org.springframework.dao.DataAccessException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception used for problems with access to data on the service layer
 * @author Terezia Slaninakova (445526)
 */
public class ServiceException extends DataAccessException {

    private Logger logger = Logger.getLogger(ItemService.class.getName());

    public ServiceException(String msg, Throwable cause) {

        super(msg, cause);
        logger.log(Level.WARNING, msg, cause);
    }

    public ServiceException(String msg) {

        super(msg);
        logger.log(Level.WARNING, msg);
    }
}
