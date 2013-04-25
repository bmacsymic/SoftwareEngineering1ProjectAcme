REM @echo off

IF "%1" NEQ "" (SET SLEEP=%1) ELSE (SET SLEEP=0)

call setClasspath

cd ..

java -cp %CLASSPATH% ctunit.AcceptanceTestRunner %SLEEP%

cd "Batch Files"

set SLEEP=
