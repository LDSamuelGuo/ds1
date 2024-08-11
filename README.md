## How to Compile and Run the Program
To initialize the RMI registry, run: make registry.
To compile and run the server, execute: make compile followed by make server.
For automated client testing, run: make client.
To compare the client output with the expected results, use: make outputCompare.
To clean up the compiled files, run: make clean.


Key Features, Interface, and Return Values:

String createUserID(): Generates a unique user ID for accessing a personal stack on the server, implementing bonus functionality.
public void pushValue(String id, Integer val);: Pushes a value onto the user's stack.
public void pushOperation(String id, String operator);: Pushes an operator onto the user's stack and performs the corresponding operation.
public Integer pop(String id);: Pops the top value from the user's stack and returns it.
public boolean isEmpty(String id);: Checks if the user's stack is empty.
public Integer delayPop(String id, Integer millis);: Waits for the specified milliseconds before performing the pop operation.
Testing
Unit/Functionality Testing: Automated client testing is performed using four input files representing different clients. The output is compared with expected results to ensure each unit functions correctly.
Integration Testing: Verifies that the client-server components interact correctly.
Stress Testing: Assesses the server's ability to handle multiple simultaneous client requests.
Acceptance Testing: Confirms the entire system functions as intended and meets the requirements.
