@echo off
echo Hello! It's a program to learn about project building.
echo There are 3 classes: Main(App), Style and Database and two libraries ^for string processing
cd compile
call run_compile.bat
cd ..
cd ant
call run_ant.bat
cd ..
cd maven
call run_maven.bat
