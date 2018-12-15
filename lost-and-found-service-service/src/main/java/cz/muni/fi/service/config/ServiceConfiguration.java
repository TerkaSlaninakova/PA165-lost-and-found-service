package cz.muni.fi.service.config;

import cz.muni.fi.api.dto.*;
import cz.muni.fi.persistence.PersistenceApplicationContext;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.service.UserServiceImpl;
import cz.muni.fi.service.facade.UserFacadeImpl;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;


@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackageClasses = {UserServiceImpl.class, UserFacadeImpl.class})
public class ServiceConfiguration {


    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        List<String> mappingFiles = new ArrayList();
        mappingFiles.add("dozerJdk8Converters.xml");
        dozer.addMapping(new DozerCustomConfig());
        dozer.setMappingFiles(mappingFiles);
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class);
        }
    }

}
