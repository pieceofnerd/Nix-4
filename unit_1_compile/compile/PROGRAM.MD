## Prepare manifest ##
echo Main-Class: ua.com.alevel.Main>MANIFEST.MF

## create jar dir ##
md build\jar

## Run compile ###
javac -sourcepath .\src -d build\classes -cp .\libs\commons-lang3-3.12.0.jar;libs\com.liferay.petra.string-4.0.2.jar src\ua\com\alevel\Main.java src\ua\com\alevel\styles\Style.java src\ua\com\alevel\databases\Database.java

## create jar file ##
jar cmf MANIFEST.MF build/jar/Program.jar  -C build/classes/ .

## Run program ##
java -cp  .\build\classes;.\libs\commons-lang3-3.12.0.jar;.\libs\com.liferay.petra.string-4.0.2.jar  ua.com.alevel.Main