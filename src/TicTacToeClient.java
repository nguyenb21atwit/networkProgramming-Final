import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TicTacToeClient {

	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	// Geeks for Geeks, Socket Programming in Java
	// https://www.geeksforgeeks.org/socket-programming-in-java/
	static Scanner clientScanner = new Scanner(System.in);

	public static void main(String[] args) {
	char[][] gameBoard = { { '_', '|', '_', '|', '_' }, { '_', '|', '_', '|', '_' }, { ' ', '|', ' ', '|', ' ' } };
		System.out.print("Please enter IP Address or 'localhost': ");
		String ipAddress = clientScanner.nextLine();
		System.out.print("Please enter port: ");
		int port = clientScanner.nextInt();
		// Creates clientSocket using info given by user -Brandan
		// Try to implement it so that I can scan a few ports or IP addresses - Brandan
		// We also need to make it so that it can take input from server and output to
		// console -Brandan
		TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, port);
		
		printBoard( gameBoard ) ;

	}

	public TicTacToeClient(String ipAdd, int portNum) {
		try {
			System.out.println("Connected to server!");
			socket = new Socket(ipAdd, portNum);
			// SendStringOverJava -
			// https://gist.github.com/chatton/8955d2f96f58f6082bde14e7c33f69a6
			while (!socket.isClosed()) {
			char[][] gameBoard = { { '_', '|', '_', '|', '_' }, { '_', '|', '_', '|', '_' }, { ' ', '|', ' ', '|', ' ' } };
				// Input -Brandan
			
			    
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				
				updateBoard(inputStream.read(), 1, gameBoard); // prints the server's move onto the client console
				
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
				
				updateBoard(outputInt, 2, gameBoard); // prints the client's move onto the client console
                System.out.println("\n\n"); // lines to separate boards
				
				dataOutputStream.writeInt(outputInt);
				dataOutputStream.flush();
			}
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}
	}
	public static void printBoard(char[][] gameBoard) {

    for (char[] line : gameBoard) {
        for (char x : line) {
            System.out.print(x);
        }
        System.out.println();
    }
}
	public static void updateBoard(int position, int player, char[][] gameBoard) {

    char character;

    if (player == 1) {
        character = 'X';
    } else {
        character = 'O';
    }

    switch (position) {

    case 1:
        gameBoard[0][0] = character;
        printBoard(gameBoard);
        break;
    case 2:
        gameBoard[0][2] = character;
        printBoard(gameBoard);
        break;
    case 3:
        gameBoard[0][4] = character;
        printBoard(gameBoard);
        break;
    case 4:
        gameBoard[1][0] = character;
        printBoard(gameBoard);
        break;
    case 5:
        gameBoard[1][2] = character;
        printBoard(gameBoard);
        break;
    case 6:
        gameBoard[1][4] = character;
        printBoard(gameBoard);
        break;
    case 7:
        gameBoard[2][0] = character;
        printBoard(gameBoard);
        break;
    case 8:
        gameBoard[2][2] = character;
        printBoard(gameBoard);
        break;
    case 9:
        gameBoard[2][4] = character;
        printBoard(gameBoard);
        break;
    default:
        break;

    }

}
}
