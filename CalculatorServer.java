import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer
{
    public static void main (String[] args)
    {
        try
        {
            CalculatorImplementation calculator = new CalculatorImplementation();
            Calculator stub = (Calculator) UnicastRemoteObject.exportObject(calculator, 0);

            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("Calculator", stub);

            System.err.println("Server ready");
        }
        catch (Exception e)
        {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
