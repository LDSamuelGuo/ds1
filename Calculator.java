// Interface defining remote operations
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

    
    public String createUserID() throws RemoteException;
    public void pushValue(String id, Integer val) throws RemoteException;
    public void pushOperation(String id, String operator) throws RemoteException;
    public Integer pop(String id)  throws RemoteException;
    public boolean isEmpty(String id) throws RemoteException;
    public Integer delayPop(String id, Integer millis) throws RemoteException;
}