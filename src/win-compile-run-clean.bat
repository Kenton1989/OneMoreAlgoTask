@echo off

echo Compiling...
javac *.java lab2algo/*.java
echo Finished compiling

echo ==========RUN==========
java ExampleApp
echo ==========END==========

echo Cleaning .class files
del *.class lab2algo\*.class
echo Program end

pause
