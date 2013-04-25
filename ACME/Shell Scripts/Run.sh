#!/bin/bash

source setClasspath.sh

cd ..

java -XstartOnFirstThread -cp $CLASSPATH acme.application.Main