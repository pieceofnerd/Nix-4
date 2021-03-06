@echo off
echo CONSOLE BUILDING:
echo Main-Class: ua.com.alevel.Main>MANIFEST.MF
md build\jar
call javac -sourcepath .\src -d build\classes -cp .\libs\commons-lang3-3.12.0.jar;libs\com.liferay.petra.string-4.0.2.jar src\ua\com\alevel\Main.java src\ua\com\alevel\styles\Style.java src\ua\com\alevel\databases\Database.java
call jar cmf MANIFEST.MF build/jar/Program.jar  -C build/classes/ .
echo.
echo ---------CONSOLE---------
call java -cp  .\build\classes;.\libs\commons-lang3-3.12.0.jar;.\libs\com.liferay.petra.string-4.0.2.jar  ua.com.alevel.Main
echo --------------------------
