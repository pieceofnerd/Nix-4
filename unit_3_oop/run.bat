@echo off
echo MAVEN BUILDING:
call mvn clean install
echo -----------------------UNIT 3-------------------------
cd ./application
call java -jar target/application-1.0-SNAPSHOT.jar
echo -------------------------------------------------------