@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -----------------------MODULE 1-------------------------
cd ./Menu
call java -jar target/Menu-1.0-SNAPSHOT.jar
echo -------------------------------------------------------