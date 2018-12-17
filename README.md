# Project for PA165 - [Lost-&-found service](https://is.muni.cz/auth/rozpis/tema?fakulta=1433;studium=822684;kod=PA165;predmet=1144638;sorter=vedouci;balik=336601;tema=336641;uplne_info=1;obdobi=7403)
The application allows to manage a Lost & Found service. Users can report their lost items with the possibility to give the type of item and the main characteristics. They can also upload a imageMimeType of how the item looked like. The responsible people for the website have the possibility to search the items added by users and set their statuses (e.g. claim received, in progress, found, etc...). They can also define categories for new items to be added. All found items need to be archived with the possibility to add the location where they were lost & then found. Once items have been confirmed missing, other users can see the list of missing items and contribute to return to the owner.

## How to run
- web app
```
mvn clean install
cd lost-and-found-service-spring-mvc
mvn cargo:run
```
- rest API
```
mvn clean install
cd lost-and-found-service-rest
mvn cargo:run
```
## Authentication
### Admin
  * username: `sethAdmin@gmail.com`
  * password: `admin`
### User
  * username: `johndoe@gmail.com`
  * password: `123`

## TODOs

### M3:
* [x] Runnable from mvn commandline @Jakub
* [ ] Web layer
   * [ ] User interface (uses REST or facade layer)
      * [ ] Item @Terka
          - Create
                * [ ] - Implement owner adding @Terka
          - Update
            * [ ] - Use list for enum, maybe enable admins to change owners / locations @Terka
          - Archive
            * [ ] - Disable editing when archived
          * [ ] Image
      * [x] Location @Jakub
          * [x] Controllers
          * [x] Views           
      * [x] Category @Jakub  
          * [x] Controllers
          * [x] Views
      * [ ] User @Gusto
          * [ ] Controllers
          * [ ] Views
          * [ ] Login form (registration not reqiured)
      * [] Different views for admin / user @Kubo
* [x] REST layer - @Gusto
   - must be accessible at http://localhost:8080/pa165/rest
   - at least one entity exposed
   - README file with instructions how to test the REST interface (e.g. CURL commands for command line)
* [x] samples layer - @Terka
   - add sample data + password security
   
### Checklist
* [ ] Proper Javadoc, @author annotations everywhere
* [ ] Facade Interfaces not referencing entities - DTOs only
* [ ] Service Interfaces not referencing DTOs - entities only
* [ ] No TODOs, commented code left
* [ ] mvn clean install works

### M2:
* [x] Create template for service, facade, api layer
* [x] Set up dozer framework to map entity instances to transfer objects
* [x] Service layer
    * [x] Custom exception class (inh. from DataAccessException) to be thrown in case of any exception on a the DAO layer;
    * [x] Add bean-mapping service (see [fi-muni/PA165](https://github.com/fi-muni/PA165/blob/master/eshop-service/src/main/java/cz/fi/muni/pa165/service/BeanMappingService.java))
    * [x] Create implementations for interfaces
    * [x] Create tests for implementations
    * [x] Create Category Service  and Tests @Terka
    * [x] Create Item Service and Tests @Terka
    * [x] Create Location Service and Tests @Gusto
    * [x] Create User Service and Tests @Jakub
    * [x] Create at least 2 non-trivial business functions + extensive tests - @Terka
* [x] Facade layer
    * [x] Create Category Facade and Tests @Jakub
    * [x] Create Item Facade and Tests @Jakub
    * [x] Create Location Facade and Tests @Terka
    * [x] Create User Facade and Tests @Gusto
* [x] API layer
    * [x] Create Category DTO @Gusto
    * [x] Create Item DTO @Gusto
    * [x] Create Location DTO @Jakub
    * [x] Create User DTO @Gusto

### Fixes for M1:
* [x] #1 equals/hashcode methods - @Gusto
* [x] #2 proper method for adding item / removing item to location
* [x] #3 JavaDoc for UserDao - @Terka [In review [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)]
* [x] #4 Replace JDBC by JPA - @Terka [In review [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)]
* [x] #5 Adjust names of tests - @Terka for User [In review [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)]
* [x] #6 Make tests independent of one another
* [x] #7 Improve cleaning of DB in teardowns - @Terka not a problem anymore after [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)
* [x] #8 Tests for when Item.status is null
* [x] #9 Fix diagrams - @Terka
* [x] #10 Missing @author annotations (CategoryDaoImplTest, Status)
* [x] #11 Add missing @NotNull annotations - @Terka [for User [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)]
* [x] #12 Move Status enum to separate package - @Terka [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)
* [x] #13 Unify declaration of exceptions [Pull 10](https://github.com/TerkaSlaninakova/PA165-lost-and-found-service/pull/10)
