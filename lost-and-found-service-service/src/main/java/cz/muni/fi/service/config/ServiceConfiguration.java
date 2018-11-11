package cz.muni.fi.service.config;

import cz.muni.fi.PersistenceApplicationContext;
import cz.muni.fi.dto.CategoryDto;
import cz.muni.fi.dto.ItemDto;
import cz.muni.fi.dto.LocationDto;
import cz.muni.fi.dto.UserDto;
import cz.muni.fi.entity.Category;
import cz.muni.fi.entity.Item;
import cz.muni.fi.entity.Location;
import cz.muni.fi.entity.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = "cz.muni.fi")
public class ServiceConfiguration {


    @Bean
    public Mapper dozer(){
        DozerBeanMapper dozer = new DozerBeanMapper();
        dozer.addMapping(new DozerCustomConfig());
        return dozer;
    }

    public class DozerCustomConfig extends BeanMappingBuilder {
        @Override
        protected void configure() {
            mapping(User.class, UserDto.class, TypeMappingOptions.mapNull(false));
            mapping(Location.class, LocationDto.class, TypeMappingOptions.mapNull(false));
            mapping(Item.class, ItemDto.class, TypeMappingOptions.mapNull(false));
            mapping(Category.class, CategoryDto.class, TypeMappingOptions.mapNull(false));        }
    }

}
