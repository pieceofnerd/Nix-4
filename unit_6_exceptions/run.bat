@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -----------------------UNIT 6-------------------------
cd ./Calendar
call java -jar target/Calendar-1.0-SNAPSHOT.jar
echo -------------------------------------------------------