import java.net.*;
import java.io.*;
import java.util.Scanner;
public class TicTacToeClient {

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	//Geeks for Geeks, Socket Programming in Java https://www.geeksforgeeks.org/socket-programming-in-java/
	static Scanner clientScanner = new Scanner(System.in);
	public static void main(String[] args) {
		System.out.println("Please enter IP Address: ");
		String ipAddress= clientScanner.nextLine();
		System.out.println("Please enter port: ");
		int port = clientScanner.nextInt();
		//Creates clientSocket using info given by user -Brandan
		//Try to implement it so that I can scan a few ports or IP addresses - Brandan
		//We also need to make it so that it can take input from server and output to console -Brandan
		TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, port);
		
	}
	public TicTacToeClient(String ipAdd, int portNum) {
		try {
			socket=new Socket(ipAdd, portNum);
			System.out.println("Connected");
		}
		catch(UnknownHostException u) {
			System.out.println(u);
		}
		catch(IOException i){
			System.out.println(i);
		}
	}
}
