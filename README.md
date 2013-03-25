# Building

The wager-service depends on:
* Java
* Maven
* Lots of jars that are centrally available
* Some jars that are not available in the central Maven repository

To build, you need to get Java and Maven installed. (See Google for how to do that). Once that's done, you need to install the  org.voltdb:voltdb-java-client:jar:3.0 locally:

1. Download VoltDB from http://voltdb.com/community/downloads.php
2. Unzip the archive, and place it somewhere (this will be referred to VOLTDB_HOME)
3. Clone the https://github.com/pettermahlen/voltdb-pom git repo and 'cd' into that directory
4. run 'mvn install:install-file -Dfile=$VOLTDB_HOME/voltdb/voltdbclient-3.0.jar -DpomFile=voltdb-java-client-3.0.pom

Once this is all done, you should be able to build using `mvn package`.

# Running

Note that in order for the service to work at all, you need a running Live Wagers Repository (https://github.com/pettermahlen/live-wagers-repository).

## From the command line

1. Build using `mvn package`
2. Run using `java -jar target/<artifact-id>.jar server src/main/config/dev.yml`

The `<artifact-id>` will generally be something like `wager-service-1.0-SNAPSHOT.jar`.

See http://dropwizard.codahale.com/getting-started/ for more information about running services created with DropWizard.

## From the IDE

Simply launch the 'WagerService' class as a regular Java Main class, and provide the command line arguments `server src/main/config/dev.yml`.

# NOTES/TODO

The cucumber tests are just sketches to illustrate roughly how you can write them. Key points are:

1. Keep the feature files very simple and free of details. Details and precision is hard, which is why we invent programming languages. Natural
language, such as that used in Gherkin feature files, is not good at being precise, so leave details to the code and specify only overall and important
points in feature files.
2. Keep step definition code grouped by 'domain concept' rather than 'feature file'. In the example code, there is a StepDefs each for Wager Service frontend, 
for the Volt repository, etc.
