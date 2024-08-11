import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.UUID;

public class CalculatorClient {
    public static void main(String[] args) {
        try {

            String clientId = UUID.randomUUID().toString(); // This generates a unique client ID. This helps differentiate client interactions with the server.
            Calculator cal = (Calculator) Naming.lookup("rmi://localhost/CalculatorService"); //  Looks up the "CalculatorService" object from the RMI registry running on localhost (the same machine).
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Enter command (pushValue, pushOperation, pop, isEmpty, delayPop, exit):");
                String input = scanner.nextLine();

                switch (input) {
                    case "pushValue": // Prompts for a value and calls the pushValue method on the server object.
                        System.out.println("Enter value:");
                        int value = Integer.parseInt(scanner.nextLine());
                        cal.pushValue(value, clientId);
                        break;
                    case "pushOperation": // Prompts for an operation and calls the pushOperation method on the server object.
                        System.out.println("Enter operation (min, max, lcm, gcd):");
                        String operator = scanner.nextLine();
                        cal.pushOperation(operator, clientId);
                        break;
                    case "pop": // Calls the pop method on the server object and displays the popped value.
                        System.out.println("Popped value: " + cal.pop(clientId));
                        break;
                    case "isEmpty": // Calls the isEmpty method on the server object and displays if the stack is empty.
                        System.out.println("Is stack empty? " + cal.isEmpty(clientId));
                        break;
                    case "delayPop": // Enter the milliseconds you want to delay a pop operation
                        System.out.println("Enter delay in milliseconds:");
                        int millis = Integer.parseInt(scanner.nextLine());
                        System.out.println("Popped value after delay: " + cal.delayPop(millis, clientId));
                        break;
                    case "exit": // Closes the scanner and exits the program.
                        System.out.println("Exiting...");
                        scanner.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Error! Unknown command.");
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); //Catches any exceptions during communication with the server and prints the stack trace for debugging.
        }
    }
}
