# Distributed Systems COMP SCI 3012 - Semester 2, 2023: Assignment 1
## ASSIGNMENT DESCRIPTION
### Objective
To gain an understanding of how remote method invocation in Java works, synchronisation, and to develop a working example of a Java RMI system. This will be essential in developing future applications as you will quickly learn all of the pitfalls inherent in developing standards-based clients and multi-threaded servers.

To gain an understanding of how a distributed system works, this first assignment phase involves developing a simple Java RMI application. This will involve developing both the client and server side of a distributed application: a simple calculator server.

The calculator server operates a stack and clients push values and operations on to the stack. While, usually, each client should have its own stack, you may use a single stack on the server. You may also assume that operations are always sensible: that is, we will only push an operator after pushing at least on value and we will only pop when there is a value on the stack. You may also assume that the operator provided will only be one of the four displayed types and that the values are always integers.

## HOW TO COMPILE AND RUN PROGRAM:
To initialise the rmi registry, you must run:
make registry 

To compile and run the server, you must run:
make compile
make server

To compile the automated client testing run:
make client

To compare the output of the Client to the expected outputs run:
make outputCompare

To clean up the compiled files run:
make clean

## ASSIGNMENT ARCHITECTURE
### Program files and structure
The program has several files:
- Calculator.java
    This is the interface that describes the methods that the client can use to interface with the server (also the server skeleton). 
- CalculatorClient.java
    This is the main client program that performs the client's operations by sending its requests to the server using the client stub on the rmi registry. This also contains the multi-client testing (through the use of multithreading), input textfile processor and handler to facilitate the process of automating the multi-client testing. 
- CalculatorImplementation.java
    This contains the implementation for the methods defined in the Calculator.java file and helper functions for those methods. 
- CalculatorServer.java
    This is the server that the client interfaces through the client stub. We instantiate a new instance of the server, then export the skeleton and bind it to the rmi registry, so that the client can access the server. 
- Makefile
    Contains the commands to make compilation & running code easier using simple commands.
- TestInput text files
    These 4 files contain a list of inputs to the server, where each file represents a client's requests. 
- Output text files & ExpectedOutput text files
    This is the output after the client performs isEmpty, pop, or delayPop. This is used to compare against the ExpectedOutput files to find issues with functionality and stability.

### Functionality
I have implemented the base functionality (pushValue, pushOperation, pop, isEmpty, & delayPop) plus the bonus marks functionality (where the clients have their own stack on the server: each client accesses their own stack, rather than the common one). How I tested them to ensure correct functionality and stability of the client and server is explained in the Testing section.

#### Key Features, Interface & Return Values
- String createUserID()
This method returns a unique user ID so that the user can access their own stack on the server, rather than utilising a shared stack. This implements the bonus marks functionality.

- public void pushValue(String id, Integer val);
This method will take user id and val and push it on to the top of the user's stack. It returns void.

- public void pushOperation(String id, String operator);
This method takes an user id and operator, which will push a String containing an operator ("min", "max", "lcm", "gcd") to the user's stack, which will cause the server to pop all the values on the stack and:
for min - push the min value of all the popped values;
for max - push the max value of all the popped values
for lcm - push the least common multiple of all the popped values;
for gcd - push the greatest common divisor of all the popped values.
This pushes the calculated value to the user's stack. It returns void.

- public Integer pop(String id); 
This method take the user's id & will pop the top of the user's stack and return it to the user.

- public boolean isEmpty(String id);
This method take the user's id and will return true if the user's stack is empty, false otherwise.

- public Integer delayPop(String id, Integer millis); 
This method will take the user's id and milliseconds & waits millis milliseconds before carrying out the pop operation as above.

### Testing 
#### Unit/Functionality testing: Validating that each software unit performs as expected. A unit is the smallest testable component of an application.
To perform unit testing, I have created an automated client testing which takes in 4 files as input (representing 4 clients) and produces an file called Output.txt. The input files contain all the commands as per the assignment description, in different orders, and illegal inputs to the server (which I have programmed to return null, such as in the instance of popping an empty user stack).

Each unit (createUserID, pushValue, pushOperation, pop, isEmpty, delayPop) was tested by the 4 files by initialising 4 threads in the automated client testing file CalculatorClient.java. Then for each of the 4 threads/client, we pass their input files into the fileInputProcessor() function, which takes the file inputs and extracts the commands and their respective values and then execute the commands in the commandExecutor() function, which sends those requests to the server. 

From there, we can run make outputCompare to compare the actual output to the expected outputs, to verify the whole system works as intended. The reason for 2 expected output files is due to the uncertainty in timing of outputs, so some threads may execute than others, causing varying outputs. 

In this case, the Output.txt matches the ExpectedOutput1.txt because the threads have happened to execute in that order in this instance. Therefore, the program has passed the unit/functionality testing. 

#### Integration testing: Ensuring that software components or functions operate together.
We have 2 main parts, clients & server. Since the client is able to successfully send requests to the server, and the server responds with 
a response which matches the expected output file, which is part of unit/functionality testing, it can be concluded that the programs have passed the integration testing. 

#### Stress testing: Testing how much strain the system can take before it fails.
To stress test the server, I have initialised 4 threads (representing 4 clients). This is executed simultaneously, putting a load on the server to ensure that it can handle multiple clients sending requests to it. Since the client or server did not crash during automated testing and the actual output matches the expected output, it can be concluded that the programs passed stress testing.

#### Acceptance testing: Verifying whether the whole system works as intended.
Considering the unit/functionality testing, integration and stress testing results, it can be concluded that the whole systems works as intended and is performing well. Therefore, it passes acceptance testing. 
