// Interface defining remote operations
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    void pushValue(int val, String clientId) throws RemoteException;
    void pushOperation(String operator, String clientId) throws RemoteException;
    int pop(String clientId) throws RemoteException;
    boolean isEmpty(String clientId) throws RemoteException;
    int delayPop(int millis, String clientId) throws RemoteException;
}
