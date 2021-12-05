import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TicTacToeClient {

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private static boolean foundPort=false;
	private static int timeout=200;
	// Geeks for Geeks, Socket Programming in Java
	// https://www.geeksforgeeks.org/socket-programming-in-java/
	static Scanner clientScanner = new Scanner(System.in);

	public static void main(String[] args) {
		// Creates clientSocket using info given by user -Brandan
        // Try to implement it so that I can scan a few ports or IP addresses - Brandan
        // We also need to make it so that it can take input from server and output to
        // console -Brandan
        System.out.print("Please enter IP Address or 'localhost': ");
        String ipAddress = clientScanner.nextLine();
        while(foundPort==false) {
        System.out.print("Please enter port: ");
        int port = clientScanner.nextInt();
        //Fastest Way to Scan Ports with Java
        //https://stackoverflow.com/questions/11547082/fastest-way-to-scan-ports-with-java
        if(portIsOpen(ipAddress,port, timeout)) {
            System.out.println("Port is open, connecting...");
            TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, port);
            foundPort=true;
        }
        else {
            System.out.println("Port is open not open, try again!");
            }
        }

    }

	public TicTacToeClient(String ipAdd, int portNum) {
		try {
			System.out.println("Connected to server!");
			System.out.println("Welcome to Tic Tac Toe!");
			socket = new Socket(ipAdd, portNum);
			// SendStringOverJava -
			// https://gist.github.com/chatton/8955d2f96f58f6082bde14e7c33f69a6
			while (!socket.isClosed()) {
				// Input -Brandan
			
			    // https://stackoverflow.com/questions/14054828/sending-and-receiving-2d-arrays-java
			    // this link helped cast
			    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			    try
                    {
                    char[][] gameBoard = (char[][]) is.readObject();  // figure out how to correctly cast this??? - Thomas
                    System.out.println( gameBoard ) ;
                    }
                catch ( ClassNotFoundException e )
                    {
                    // TODO Auto-generated catch block
                    e.printStackTrace() ;
                    }
			    
			    
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				String message = dataInputStream.readUTF();
				System.out.println("FROM SERVER: " + message);
				/*
				 * BufferedReader fromServer = new BufferedReader(new
				 * InputStreamReader(socket.getInputStream()));
				 * System.out.println(fromServer.readLine());
				 */
				// Output -Brandan
				OutputStream outputStream = socket.getOutputStream();
				DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

				input = new DataInputStream(System.in);
				System.out.println(input);
				System.out.print("Send to Server a Number (1-9): ");
				int outputInt = clientScanner.nextInt();
				dataOutputStream.writeInt(outputInt);
				dataOutputStream.flush();
			}
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	public static boolean portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            socket.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
