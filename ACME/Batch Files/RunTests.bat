call setClasspath
cd ..

java org.junit.runner.JUnitCore tests.UnitTests > AllTests.txt
java org.junit.runner.JUnitCore tests.IntegrationTests >> AllTests.txt

cd Batch Files
