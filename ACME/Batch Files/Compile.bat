REM @echo off

REM
REM This script requires that the "javac" command be on the system command path.
REM This can be accomplished by modifying the path statement below
REM to include "C:\Program Files\Java\jdk1.6.0_03\bin" (or whatever your path is).
REM Alternatively, you could add the path to "javac" to the PATH Environment variable: 
REM   Settings-->Control Panel-->System-->Advanced-->Environment Variables-->Path
REM


REM clean all .class files out of the bin directory

call setClasspath

cd ..\bin
erase /S /Q *.class
cd ..

cd lib

@echo off
echo %PROCESSOR_ARCHITECTURE% > processorCheck.txt
find "64" processorCheck.txt
set USE64=%ERRORLEVEL%
del processorCheck.txt
IF %USE64% == 0 (set fileName=swt-3.6.2-win32-win32-x86_64.jar) ELSE (set fileName=swt-3.6.2-win32-win32-x86.jar)

IF EXIST swt.jar (del swt.jar)

copy %fileName% swt.jar

cd ..

echo %CLASSPATH%

javac -d bin\ -cp %CLASSPATH% src\acme\objects\*.java src\acme\persistence\*.java src\acme\application\*.java src\acme\business\*.java src\acme\presentation\*.java src\org\eclipse\wb\swt\*.java

javac -d bin\ -cp %CLASSPATH% src\tests\objectTests\*.java
javac -d bin\ -cp %CLASSPATH% src\tests\persistence\*.java
javac -d bin\ -cp %CLASSPATH% src\tests\businessTests\*.java
javac -d bin\ -cp %CLASSPATH% src\tests\integration\*.java
javac -d bin\ -cp %CLASSPATH% src\tests\*.java

cd Batch Files

