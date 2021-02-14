package sample;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private ServerSocket serverSocket;
    Server() {
        try {
            serverSocket = new ServerSocket(44444);
            if(!DataBase.getInstance().open()){
                System.out.println("Couldn't open Database.");
            }
            while(true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        }catch(IOException e){
            System.out.println("Server starts : "+e);
        }
    }

    public void serve(Socket clientSocket){
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        new ReadServer(networkUtil);
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
