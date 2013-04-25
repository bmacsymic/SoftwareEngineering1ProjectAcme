#!/bin/bash

source setClasspath.sh

cd ..

java -cp $CLASSPATH org.junit.runner.JUnitCore tests.AllTests