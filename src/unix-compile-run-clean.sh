#!/bin/bash

echo Compiling...
javac *.java lab2algo/*.java
echo Finished compiling

echo ==========RUN==========
java ExampleApp
echo ==========END==========

echo Cleaning .class files
rm -f *.class lab2algo/*.class
echo The program will terminate in 30 seconds. Press Ctrl+C to exit directly.
sleep 30