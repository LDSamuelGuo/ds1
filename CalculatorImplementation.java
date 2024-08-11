import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {
    
    //This private member variable holds the map that associates client IDs with their respective stacks.
    private final Map<String, Stack<Integer>> stacks;

    protected CalculatorImplementation() throws RemoteException {
        super();
        stacks = new HashMap<>();  
    }  

    //This method retrieves the existing stack for a client or creates a new one if it doesn't exist.

    private Stack<Integer> getStackForClient(String clientId) {
        Stack<Integer> stack = stacks.get(clientId);
        if (stack == null) {
            stack = new Stack<>();
            stacks.put(clientId, stack);
        }
        return stack;
    }

    // pushValue :: Pushes a new value onto the client's stack.
    @Override
    public synchronized void pushValue(int val, String clientId) throws RemoteException {
        getStackForClient(clientId).push(val);
    }

    // pushOperation :: Handles various operations (min, max, gcd, lcm)
    @Override
    public synchronized void pushOperation(String operator, String clientId) throws RemoteException {
        
        Stack<Integer> stack = getStackForClient(clientId);

        // Checking for an empty stack and printing an error message (consider throwing an exception instead of just printing).
        if (stack.isEmpty()) 
        {
            System.out.println("Error: Stack is empty for operation " + operator);
            return;
        }

        // Creating a temporary ArrayList to store popped values for efficient calculation.
        List<Integer> values = new ArrayList<>();
        
        while (!stack.isEmpty()) {
            values.add(stack.pop());
        }
        
        // Using the Collections.min and Collections.max methods for finding minimum and maximum values, respectively.
        if (operator.equals("min")) 
        {
            int min = Collections.min(values);
            stack.push(min);
        } 
        else if (operator.equals("max")) 
        {
            int max = Collections.max(values);
            stack.push(max);
        } 

        // Calling the gcd and lcm helper methods for calculating greatest common divisor and least common multiple.
        else if (operator.equals("gcd")) 
        {
            stack.push(gcd(values));
        }
        else if (operator.equals("lcm")) 
        {
            stack.push(lcm(values));
        }

        // Throwing a RemoteException for unknown operators.
        else {
            throw new RemoteException("Unknown operator: " + operator);
        }
    }

    // pop :: Pops the top element from the client's stack and throws an exception if the stack is empty.
    @Override
    public synchronized int pop(String clientId) throws RemoteException {
        Stack<Integer> stack = getStackForClient(clientId);
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty.");
        }
        return stack.pop();
    }

    // isEmpty :: Checks if the client's stack is empty.
    @Override
    public synchronized boolean isEmpty(String clientId) throws RemoteException {
        if(getStackForClient(clientId).isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private int gcd(List<Integer> values) {
        if (values.isEmpty()) return 0;
        int gcd = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            gcd = gcd(gcd, values.get(i));
        }
        return gcd;
    }

    private int gcd(int a, int b) {
        if (b == 0) 
        {
            return a;
        }
        else
        {
            return gcd(b, a % b);
        }
        
    }

    // This method calculates the LCM using the efficient formula Math.abs(a * b) / gcd(a, b).
    private int lcm(List<Integer> values) {
        if (values.isEmpty()) return 0;
        int lcm = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            lcm = lcm(lcm, values.get(i));
        }
        return lcm;
    }

    private int lcm(int a, int b) {
        return Math.abs(a * b) / gcd(a, b);
    }

    // delapPop :: This method introduces a delay before popping from the stack. It uses TimeUnit.MILLISECONDS.sleep to pause for the specified millis.
    @Override
    public synchronized int delayPop(int millis, String clientId) throws RemoteException {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);    // Delay the pop operation.
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Interrupted during delay.", e);  // Handle interruption.
        }
        return pop(clientId);   // Perform the pop operation after the delay.
    }

}
