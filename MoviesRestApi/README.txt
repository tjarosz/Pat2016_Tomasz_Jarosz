 - To package the Application:

mvn package

- To setup the h2 database run:

java -jar target/MoviesRestApi-1.0-SNAPSHOT.jar db migrate movies.yml


- To run the server run.

java -jar target/MoviesRestApi-1.0-SNAPSHOT.jar server movies.yml


- To post movie into the application:

curl -H "Content-Type: application/json" -X POST -d '{"title":"Title","director":"Director","actors":[{"name":"m1","surname":"s1"},{"name":"n2","surname":"s2"}]}' http://localhost:8080/movies

- To post actor into the application:

curl -H "Content-Type: application/json" -X POST -d '{"name":"name","surname":"surname","dateOfBirth":"12/03/2000"}' http://localhost:8080/actors

- To delete movie:

curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/movies/1

- To delete actor:

curl -H "Content-Type: application/json" -X DELETE http://localhost:8080/actors/1

- To update movie:

curl -H "Content-Type: application/json" -X PUT -d '{"title":"Other title",  "director":"Another Dir"}' http://localhost:8080/movies/1 

- To update actor:

curl -H "Content-Type: application/json" -X PUT -d '{"name":"Other name","surname":"surname","dateOfBirth":"12/03/2000"}' http://localhost:8080/actors/1 