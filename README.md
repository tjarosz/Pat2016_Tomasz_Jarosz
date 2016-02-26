# Running The Application

* To package the Application:
```
mvn package
```
* To setup the h2 database run:
```
java -jar target/MoviesRestApi-1.0-SNAPSHOT.jar db migrate movies.yml

```
* To run the server run.
```
java -jar target/MoviesRestApi-1.0-SNAPSHOT.jar server movies.yml

```
# Using The Application

* To post movie into the application:
```
curl -H "Content-Type: application/json" -X POST http://localhost:8080/movies/title/TitleExample
```
or
```
curl -H "Content-Type: application/json" -X POST -d '{"Title":"Title","Director":"Director","Actors":"m1 s1, m2 s2"}' http://localhost:8080/movies
```
* To post actor into the application:
```
curl -H "Content-Type: application/json" -X POST -d '{"name":"name","surname":"surname","dateOfBirth":"12/03/2000"}' http://localhost:8080/actors
```
* To delete movie:
```
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/movies/1
```
* To delete actor:
```
curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/actors/1
```
* To update movie:
```
curl -H "Content-Type: application/json" -X PUT http://localhost:8080/movies/title/TitleExample/IdMovie

```
or
```
curl -H "Content-Type: application/json" -X PUT -d '{"Title":"Other title","Director":"Another Dir"}' http://localhost:8080/movies/1 
```
* To update actor:
```
curl -H "Content-Type: application/json" -X PUT -d '{"name":"Other name","surname":"surname","dateOfBirth":"12/03/2000"}' http://localhost:8080/actors/1 
```