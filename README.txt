The project is divided in two submodule:

data -> takes care of parsing the csv data and retrieves the information about impressions
server -> the APIs

data is designed to be loosely couple by exposing an interface AsyncImpressionExtractor, that gives to its client the impressions according to the task.
It processes the csv in an async way because if the file is big enough, the parsing might not be done by the time a http client consumes the APIs: all
methods return a CompletionStage.
In order to make the code more readable, since the data never changes, the interface allows to get the data from the csv as a bunch of immutable sets.

The server module implements the task's APIs by using the reactive library Mutiny!, with "Uni.createFrom().completionStage".
The only error considered is a parsing error. In that case, the error is transformed in a JSON (ErrorDto).

All modules have a bunch of tests.

START THE PROJECT
use the script "build-and-start-server.sh".

All apis can be called using curl:

curl http://localhost:8082/impressions/by_device

curl http://localhost:8082/impressions/by_dayofweek

curl http://localhost:8082/impressions/by_dayofmonth

curl http://localhost:8082/impressions/by_hour