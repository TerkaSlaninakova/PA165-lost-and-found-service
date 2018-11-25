package cz.muni.fi.service.config;

import cz.muni.fi.dto.*;
import cz.muni.fi.persistence.PersistenceApplicationContext;
import cz.muni.fi.persistence.entity.Category;
import cz.muni.fi.persistence.entity.Item;
import cz.muni.fi.persistence.entity.Location;
import cz.muni.fi.persistence.entity.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Collections;


@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi")
public class ServiceConfiguration {


    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        dozer.setMappingFiles(Collections.singletonList("dozerJdk8Converters.xml"));
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDTO.class, TypeMappingOptions.mapNull(false));
            mapping(Location.class, LocationDTO.class, TypeMappingOptions.mapNull(false));
            mapping(Item.class, ItemDTO.class, TypeMappingOptions.mapNull(false));
            mapping(ItemDTO.class, ItemCreateDTO.class, TypeMappingOptions.mapNull(false));
            mapping(Category.class, CategoryDTO.class, TypeMappingOptions.mapNull(false));
            mapping(CategoryCreateDTO.class, CategoryDTO.class, TypeMappingOptions.mapNull(false));}
    }

}
