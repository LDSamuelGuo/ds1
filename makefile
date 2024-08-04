
# the compile command compiles all java files
compile:
	javac -d ./ *.java

# registry starts the rmiregistry
registry:
	rmiregistry &

# clean removes all compiled files
clean:
	rm -f *.class

# server runs the server
server:
	java -cp ./ CalculatorServer &
	
# client runs the client, takes input from the TestInput files
client: CalculatorClient.class
	java -cp ./ CalculatorClient > Output.txt

outputCompare:
	diff Output.txt ExpectedOutput0.txt || true
	diff Output.txt ExpectedOutput1.txt || true 

test: clean compile registry server client outputCompare
