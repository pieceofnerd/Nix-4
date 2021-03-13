@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -------TASK 2-------
call java -jar target/Test-1.0-SNAPSHOT.jar
echo -----------------------
