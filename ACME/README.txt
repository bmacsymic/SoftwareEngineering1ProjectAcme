Group 9
-------
Bradley Macsymic
Alex Salomon
Kyle MacKinnon

CVS Location: aviary.cs.umanitoba.ca/home/student/ummack57/ACME_Store/repository

Contents of Submission
----------------------
-this file
-all source files in a folder called src (see Packages)
-all class files built from that source in a folder called bin 
-a lib folder that contains any necessary jar files
-an images folder with some default images
-a Windows batch file named Run.bat that executes the application without preconditions
-a Windows batch file named RunTests.bat that executes all unit tests without preconditions 
-a Windows batch file named Compile.bat that deletes the old .class files and re compiles the project
-a Windows batch file named setClassPath.bat that modifies the class path variable
-A Windows batch files named RestoreDB.bat in the "database" folder that returns the
database to a known initial state
-a text file called AllTests.txt containing the output of the RunTests script
-Iteration3_planning.txt contains our meetings notes for what we planned to do or not do for
iteration 3
-A pdf/docx file Retrospective Activity which contains a duplicate of the file that was 
submitted via physical handin
-A text file User Stories with all the user stories from Iteration 2, and the new ones
added for iteration 3

Packages
--------
acme.application - Includes Main.java (Entry point of the application)
acme.business - Logic Layer
acme.objects - Domain Objects
acme.persistence - Data layer
acme.presentation - Graphical User Interface
org.eclipse.wb.swt - Dynamically added by Eclipse for GUI support
tests - Runs all the tests in the below packages
tests.businessTests
tests.integration
tests.objectTests
tests.persistence - Includes DataAccessStub.java

Major Changes made for Iteration 3
----------------------------------
Structure:
Additional code was added to the GUI section to make it work in conjunction with
the customer testing. New java classes were added for the new items that
were implemented (Customer tests, integration tests, more unit tests, Window addition
in the GUI)

Behaviour:
No major behavioural changes were made in this iteration. At the end of iteration 2 we
already had the services injection method implemented so we didn't need to add the
database injection idea since it was already in from iteration 2.

Issues Not Resolved in Iteration 3
----------------------------------
We did not implement our last three low priority Big User Stories:
-Customer View Order History
-Staff View Statistics
-Staff Reset Customer Information

Reasoning for this was that we underestimated our time requirements for other features of
the program, such as adding all types of Tests(Unit, Customer, Integration) and in the
refactoring and bug checking needed from the previous iterations.

