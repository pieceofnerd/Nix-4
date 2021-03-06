@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -------Maven -------
call java -jar target/maven-1.0-SNAPSHOT.jar
echo --------------------