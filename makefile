# the compile command compiles all java files
compile: *.java
		
javac *.java

# registry starts the rmiregistry
registry:
	rmiregistry &

# clean removes all compiled files
clean:
	rm *.class


server:
	java -cp ./ CalculatorServer &


client: CalculatorClient.class
	java -cp ./ CalculatorClient > Output.txt


outputCompare: 
	diff Output.txt ExpectedOutput1.txt & diff Output.txt ExpectedOutput2.txt
