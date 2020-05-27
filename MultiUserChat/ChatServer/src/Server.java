import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: ying
 * Date: 3/14/20
 * Time: 16:43
 * Description: No Description
 */
public class Server extends Thread{

    private final int serverPort;

    private ArrayList<ServerWorker> workerList = new ArrayList<>(); // collection of serverworkers

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public ArrayList<ServerWorker> getWorkerList() {
        return workerList;
    }


    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort); // listen to the port
            while(true) {
                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept(); // accept incoming connections
                System.out.println("Accept connection from " + clientSocket);
                // In order for those server workers to access the server instance, we also pass the server as part of the parameter
                ServerWorker worker = new ServerWorker(this,  clientSocket); // worker to handle the communications with the client socket
                workerList.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }
}

