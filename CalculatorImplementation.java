import java.rmi.RemoteException;
import java.util.*;

public class CalculatorImplementation implements Calculator {
    
     // Constructor for CalculatorImplementation 
    //if there is an issue with the RMI
    
    private Map<String, Stack<Integer>> values = new HashMap<>();

  
    public CalculatorImplementation() throws RemoteException{
        super();
    };
  
     // Helper function to calculate GCD of two numbers 
    //@param x First number
    // @param y Second number
    // The GCD of x and y
    
    private static int gcdHelper(int x, int y)
    {   
        if(y == 0)
        {
            return x;
        }
        return gcdHelper(y, x % y);
    }

    // this is the helper function for the lcm 
    // function. it returns the lcm for 2
    // numbers. 
    private static int lcmHelper(int x, int y)
    {
        return (x * y) / gcdHelper(x, y);
    }

    // LCM returns the least common multiple of a
    // stack of integers.
    private static int lcm(Stack<Integer>stack)
    {
        int ans = stack.get(0);
        
        for (int i = 1; i < stack.size(); i++) {
            // calculate the lcm of the current lcm 
            // and next element using the lcmHelper 
            // function
            ans = lcmHelper(ans, stack.get(i));
        }
        return ans;
    }

    // this function returns the greatest common divisor 
    // of the array by finding the gcd of the current gcd with 
    // the next avaliable element in the array.
    private static int gcd(Stack<Integer>stack)
    {   
        int ans = stack.get(0);
        for (int i = 1; i < stack.size(); i++) {
            ans = gcdHelper(ans, stack.get(i));
        }

        return ans;
    }

     
     //Helper function to calculate GCD of two numbers 
     // First number
     //@param y Second number
     //@return The GCD of x and y
     
    public String createUserID()
    {
        
        String Id = UUID.randomUUID().toString();
        
        this.values.put(Id, new Stack<>());
      
        return Id;
    }

    
     // Pushes a value on to the stack associated with the given id
     // @param id, The user id
     // @param val, the value to be pushed onto the stack 
     //synchronized to ensure thread safety
     
    public void pushValue(String id, Integer val) 
    {
        this.values.get(id).push(val);
    }

   
     // Applies operations on the stack associated with the user's unique id
     // @param id, user id
     // @param operator, the operation to be applied 
     // synchronized to ensure thread safety
     
    public void pushOperation(String id, String operator)
    {
        // check if the stack has values, else do nothing
        if(this.values.get(id).size() > 0)
        {
            int ans;
            if(operator.contains("min"))
            {
                
                ans = Collections.min(this.values.get(id));
            }
            else if (operator.contains("max"))
            {
                ans = Collections.max(this.values.get(id));
            }
            else if (operator.contains("gcd"))
            {
                
                ans = gcd(this.values.get(id));
            }
            else
            {
                
                ans = lcm(this.values.get(id));
            }
            
            this.values.get(id).clear();
            
            this.values.get(id).add(ans);
        }
    }

    // this function takes the user's id and 
    // pops the value from the stack if there 
    // is a value on the stack, else returns null
    public Integer pop(String id) 
    {
        if(this.values.get(id).size() == 0)
        {
            
            return null;
        }
        else
        {
           
            return this.values.get(id).pop();
        }
    }
    
    // this function takes the user ID &
    // determines whether their stack is empty
    // it will return true for empty stack, and 
    // false otherwise. 
    public boolean isEmpty(String id)
    {
        return this.values.get(id).isEmpty();
    }

    // This is the delay pop function
    // it takes the client ID and the time to sleep

    public Integer delayPop(String id, Integer millis) 
    {    
        
        if(this.values.get(id).size() > 0)
        { 
            int ans = -1;
           
            try {
                Thread.sleep(millis);
                
                ans = this.values.get(id).pop();
            } catch (Exception e) {
                
                System.err.println("Server exception: " + e.toString());
                e.printStackTrace();
            }
            return ans;
        }
        else
        {
           
            return null;
        }
    }
}
