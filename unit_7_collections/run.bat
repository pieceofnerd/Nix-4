@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -----------------------UNIT 7-------------------------
cd ./Application
call java -jar target/Application-1.0-SNAPSHOT.jar
echo -------------------------------------------------------