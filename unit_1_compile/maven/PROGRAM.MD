## generate a maven prject ##
 mvn archetype:generate -DgroupId=ua.com.alevel -DartifactId=maven -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

## create jar file ##
mvn clean install

## Run program ##
java -jar target/maven-1.0-SNAPSHOT.jar