package cz.muni.fi.service;

import org.dozer.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author Augustin Nemec
 */
public interface BeanMappingService {

    /**
     * Maps collection of objects to given class.
     * Used for example to map DTO objects to entities and vice-versa
     * @param objects collection of objects to be mapped
     * @param mapToClass class to map to
     * @param <T> class to map to
     * @return list of mapped objects
     */
    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps single object to given class
     * @param u object to be mapped
     * @param mapToClass taget class to map to
     * @param <T> class to map to
     * @return mapped object
     */
    public  <T> T mapTo(Object u, Class<T> mapToClass);

    /**
     * Returns instance of the mapper
     * @return mapper instance
     */
    public Mapper getMapper();

}
