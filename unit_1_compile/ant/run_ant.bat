@echo off
echo ANT BUILDING:
echo Main-Class: com.metanit.Main>MANIFEST.MF
call ant clean
call ant compile
call ant jar
echo -------Ant-------
call ant run
echo -----------------