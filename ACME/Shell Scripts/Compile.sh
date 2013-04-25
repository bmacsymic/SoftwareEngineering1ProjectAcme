#!/bin/bash

#replace swt.jar with correct version only if the file is actually different
cd ../lib/
rsync swt-3.6.2-macosx-x86_64.jar swt.jar

# clean all .class files out of the bin directory
cd ../bin
find . -type f -name '*.class' -print0 | xargs -0 rm -f

#set the classpath variable
cd ..
source Shell\ Scripts/setClasspath.sh

#compile the program
javac -d bin -cp $CLASSPATH src/acme/objects/*.java src/acme/persistence/*.java src/acme/application/*.java src/acme/business/*.java src/acme/presentation/*.java src/org/eclipse/wb/swt/*.java

#compile the tests
javac -d bin -cp $CLASSPATH src/tests/objectTests/*.java
javac -d bin -cp $CLASSPATH src/tests/persistence/*.java
javac -d bin -cp $CLASSPATH src/tests/businessTests/*.java
javac -d bin -cp $CLASSPATH src/tests/integration/*.java
javac -d bin -cp $CLASSPATH src/tests/*.java