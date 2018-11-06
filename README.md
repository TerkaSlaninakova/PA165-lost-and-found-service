# Project for PA165 - [Lost-&-found service](https://is.muni.cz/auth/rozpis/tema?fakulta=1433;studium=822684;kod=PA165;predmet=1144638;sorter=vedouci;balik=336601;tema=336641;uplne_info=1;obdobi=7403)
The application allows to manage a Lost & Found service. Users can report their lost items with the possibility to give the type of item and the main characteristics. They can also upload a photo of how the item looked like. The responsible people for the website have the possibility to search the items added by users and set their statuses (e.g. claim received, in progress, found, etc...). They can also define categories for new items to be added. All found items need to be archived with the possibility to add the location where they were lost & then found. Once items have been confirmed missing, other users can see the list of missing items and contribute to return to the owner.


## TODOs

### Fixes for M1:
* [ ] #1 equals/hashcode methods - @Gusto
* [ ] #2 proper method for adding item / removing item to location
* [ ] #3 JavaDoc for UserDao
* [ ] #4 Replace JDBC by JPA - @Terka
* [ ] #5 Adjust names of tests
* [ ] #6 Make tests independent of one another
* [ ] #7 Improve cleaning of DB in teardowns
* [ ] #8 Tests for when Item.status is null
* [ ] #9 Fix diagrams - @Terka
* [ ] #10 Missing @author annotations (CategoryDaoImplTest, Status)
* [ ] #11 Add missing @NotNull annotations
* [ ] #12 Move Status enum to separate package
* [ ] #13 Unify declaration of exceptions for UserDaoImpl and UserDao
