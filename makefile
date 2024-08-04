
compile: *.java
	javac -d ./ *.java


registry:
	rmiregistry &


clean:
	rm *.class


server:
	java -cp ./ CalculatorServer &


client: CalculatorClient.class
	java -cp ./ CalculatorClient > Output.txt


outputCompare: 
	diff Output.txt ExpectedOutput1.txt & diff Output.txt ExpectedOutput2.txt
