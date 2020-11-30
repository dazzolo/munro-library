# Munro Library

The Munrlo Library it's written using Spring Boot, Spring JPA and H2 in-memory DB.
It exposes a sinlge API, `/munros`, which returns a list of munros and munro tops, in JSON format, based  on the
following parameters passed in the querystring:

- category: non required, case insensitive, accepted values are "MUN" (as in munro) and "TOP" (as in munro top), any other value is
considered invalid and will be rejected. Only munros of the specified category will be returned in the result.
If not specified, both categories will be returned. Munros with empty category in the DB are never returned.
- minHeight: non required, this is a double value which excludes all the munros and munro tops with lower height
from the results. By default it's set to Double.MIN_VALUE. Non numeric values are rejected.
- maxHeight: non required, this is a double value which excludes all the munros and munro tops with greater height
from the results. By default it's set to Double.MAX_VALUE. Non numeric values are rejected.
- sortingCriteria: non required, case insensitive, accpted values are "name" and "height", any other value is considered
invalid and will be rejected. This will make the results be ordered by the specified criteria, by defualt they will be ordered by name.
- sortingOrder: non required, case insensitive, accpted values are "asc" (as in ascendent) and "desc" (as in descendent),
any other value is considered invalid and will be rejected. This will make the results be ordered by the specified order,
by defualt that would be asc.
- maxResults: non required, this is an integer value, any other value will be rejected. This will limit the number of results to
be less than the specified amount.

## Build and run locally

To be build locally run:

`mvn clean install`

in the project root folder. This command will generate an executable jar and also run a set of unit and integration tests.

To run the server and expose the API run:

`java -jar target/munro-library-0.0.1-SNAPSHOT.jar`

The code imports data into an in-memory DB at startup time, so, while running the application, you can access the DB console at

http://localhost:8080/h2-console

default credentials are set

username: `sa`
password: ``

