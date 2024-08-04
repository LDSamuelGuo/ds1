import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.io.File;

public class CalculatorClient implements Runnable {
	Calculator stub;
	/** 
     * Constructor fot Calculator Client
     *  
     */
	public CalculatorClient(Calculator stub)
	{
		this.stub = stub;
	}
    
	private void commandExecutor(String cmds[], String id)
	{	
		if(cmds[0].contains("pushOperation"))
		{
			try {
				this.stub.pushOperation(id, cmds[1]);
			} catch (Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
		else if(cmds[0].contains("pushValue"))
		{
			try {
				this.stub.pushValue(id, Integer.valueOf(cmds[1]));
			} catch (Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
		else if(cmds[0].contains("pop"))
		{
			try {
				System.out.println(this.stub.pop(id));
			} catch (Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
		else if(cmds[0].contains("isEmpty"))
		{
			try {
				System.out.println(this.stub.isEmpty(id));
			} catch (Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
		else if(cmds[0].contains("delayPop"))
		{
			try {
				System.out.println(this.stub.delayPop(id, Integer.valueOf(cmds[1])));
			} catch (Exception e) {
				System.err.println("Error: " + e.toString());
			}
		}
	};


    /**
     * Read and process input and executes them
     * , the user id to identify which user to execute for
     * The method reads input from file, splits into arguments and command 
     * and then executes using the argument_exe method
     * Handles exceptions if oeprations fail 
     */
	public void fileInputProcessor(String id)
	{
		try {
			
			String file = "./TestInput" + Thread.currentThread().getName() + ".txt";
			// the getName() method includes "Thread-", so it is filtered out
			file = file.replace("Thread-", "");

			
			Scanner in = new Scanner(new File(file));
			// each command in the input test file is delimited with newline
			
			in.useDelimiter("\n");

	
			boolean exit = false;
			while(exit == false)
			{
				String cmd="";
				cmd+=in.nextLine();
				
				String cmds[] = cmd.split(" ");
				// exit if the user types into the command "exit"
				// so the while loop exits
				if(cmds[0].contains("exit"))
				{
					exit = true;
				}
				
				commandExecutor(cmds, id);
			}
			
			in.close();
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
	}

	
    /**
     * Method gets a user ID by calling userID method on the stub
     * Then calls fileprocessor to process commands 
     * Handles exceptions if the stub method call fails 
     */
	public void run()
	{
		String id = "empty";
		try {
			// generate a unique user id so that 
			// user can access only their stack
			id = this.stub.createUserID();
		} catch (Exception e) {
			System.err.println("Error: " + e.toString());
		}
		
		fileInputProcessor(id);
	}
	
    public static void main(String[] args)
    {
        String host = (args.length < 1) ? null : args[0];
		try {
			// find the rmiregistry 
			Registry registry = LocateRegistry.getRegistry(host);
			// from the rmiregistry, we lookup the stub with the name "Calculator"
			Calculator stub = (Calculator) registry.lookup("Calculator");

			
			for (int i = 0; i < 4; i++) {
				// we pass the client's stub to the 
				Thread t = new Thread(new CalculatorClient(stub));
				t.start();
			}
		} catch (Exception e) {
			System.err.println("CalculatorClient exception: " + e.toString());
			e.printStackTrace();
		}
    }
}
