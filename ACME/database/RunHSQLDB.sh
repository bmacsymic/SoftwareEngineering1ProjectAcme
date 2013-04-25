#!/bin/bash

CLASSPATH='.:../lib/hsqldb.jar:$CLASSPATH'

java -cp $CLASSPATH org.hsqldb.util.DatabaseManagerSwing -url "jdbc:hsqldb:acme"