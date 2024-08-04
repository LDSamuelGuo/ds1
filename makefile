# the compile command compiles all java files
compile: *.java
javac -d destDir Calculator.java CalculatorClient.java CalculatorImplementation.java CalculatorServer.java

# registry starts the rmiregistry
registry:
	rmiregistry &

# clean removes all compiled files
clean:
	rm *.class

# server starts the CalculatorServer
server:
	java -cp ./ CalculatorServer &

# client runs the CalculatorClient and outputs to Output.txt
client: CalculatorClient.class
	java -cp ./ CalculatorClient > Output.txt

# outputCompare compares the Output.txt with ExpectedOutput1.txt and ExpectedOutput2.txt
outputCompare: 
	diff Output.txt ExpectedOutput1.txt & diff Output.txt ExpectedOutput2.txt

