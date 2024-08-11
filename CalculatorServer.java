import java.rmi.Naming; // For binding remote objects to the registry.
import java.rmi.registry.LocateRegistry; // For creating and accessing the registry.

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // It creates a registry on port 1099 using LocateRegistry.createRegistry(1099)
            LocateRegistry.createRegistry(1099);


            CalculatorImplementation CalImpl = new CalculatorImplementation();

            // It binds the CalculatorImplementation object to the registry with the name "CalculatorService "using Naming.rebind("CalculatorService", CalImpl).
            // This allows clients to look up and access the remote object using this name.
            System.out.println("Calculator Running..."); 
            Naming.rebind("CalculatorService", CalImpl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
