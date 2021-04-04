@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -----------------------UNIT 5-------------------------
cd ./Menu
call java -jar target/Menu-1.0-SNAPSHOT.jar
echo -------------------------------------------------------