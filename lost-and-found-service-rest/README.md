# CURL commands for REST interface

#### Get all items:
```
curl -i -X GET http://localhost:8080/pa165/rest/items
```
#### Get items by category:
```
curl -i -X GET http://localhost:8080/pa165/rest/items/category={categoryName}
```
#### Get items by id:
```
curl -i -X GET http://localhost:8080/pa165/rest/items/{id}
```
#### Remove item:
```
curl -i -X DELETE http://localhost:8080/pa165/rest/items/{id}
```
#### Create lost Item:
```
curl -i -X POST -H "Content-Type: application/json" --data '{"name":"test","type":"test","characteristics":"test", "lostLocationId":"1", "ownerId":"1"}' http://localhost:8080/pa165/rest/items/createLost
```
#### Create founded Item:
```
curl -i -X POST -H "Content-Type: application/json" --data '{"name":"test","type":"test","characteristics":"test", "foundLocationId":"1"}' http://localhost:8080/pa165/rest/items/createFound
```
#### Add category to Item:
```
curl -i -X POST -H "Content-Type: application/json" --data '{"id":"{categoryId}"}' http://localhost:8080/pa165/rest/items/{id}/category
```
#### Remove category from Item:
```
curl -i -X DELETE -H "Content-Type: application/json" --data '{"id":"{categoryId}"}' http://localhost:8080/pa165/rest/items/{id}/category
```
#### Change owner of Item:
```
curl -i -X POST -H "Content-Type: application/json" --data '{"id":"{userId}"}' http://localhost:8080/pa165/rest/items/{id}/owner
```