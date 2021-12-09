import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TicTacToeClient {

	private static Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private static boolean foundPort=false;
	private static boolean portExists=false;
	private static int timeout=200;
	static int playerScore = 0;
	static int player2Score = 0;
	// Geeks for Geeks, Socket Programming in Java
	// https://www.geeksforgeeks.org/socket-programming-in-java/
	static Scanner clientScanner = new Scanner(System.in);

	public static void main(String[] args) {
		char[][] gameBoard = { { '_', '|', '_', '|', '_' }, { '_', '|', '_', '|', '_' }, { ' ', '|', ' ', '|', ' ' } };
		boolean gameOver = false;
		boolean playAgain = true;
		// Creates clientSocket using info given by user -Brandan
        // Try to implement it so that I can scan a few ports or IP addresses - Brandan
        // We also need to make it so that it can take input from server and output to
        // console -Brandan
        System.out.print("Please enter IP Address or 'localhost': ");
        String ipAddress = clientScanner.nextLine();
        System.out.print("Please enter port: ");
        int port = clientScanner.nextInt();
        TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, port);
        	System.out.println("Connected to server!");
      		printBoard(gameBoard);
			System.out.println("Welcome to Tic Tac Toe!!");
			while(playAgain) {
				while (!gameOver) {
					player1Move(gameBoard);
					gameOver = checkGameOver(gameBoard);
					if (gameOver) {
						break;
					}
					player2Move(gameBoard);
					gameOver = checkGameOver(gameBoard);
					if (gameOver) {
						break;
					}
				}
				System.out.println("Player 1 Score: " + playerScore);
				System.out.println("Player 2 Score: " + player2Score);
				System.out.println("Waiting for Host to Restart or End Game.");
				try {
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				int replayHost = dataInputStream.readInt();
				String result=checkPlayAgain(replayHost);
				switch (result) {
				case "Y":
				case "y":
					playAgain = true;
					System.out.println("Playing again");
					resetBoard(gameBoard);
					gameOver = false;
					printBoard(gameBoard);
					break;

				case "N":
				case "n":
					playAgain = false;
					System.out.println("Game Over!");
					break;
				default:
					break;
					}
				}
				catch(IOException z) {
					System.out.println("Error "+z);
				}
			}
	}
        /*portIsOpen(ipAddress,port, timeout);
        //Fastest Way to Scan Ports with Java
        //https://stackoverflow.com/questions/11547082/fastest-way-to-scan-ports-with-java
        if(portExists) {
            System.out.println("Port is open, connecting...");
            TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, port);
            foundPort=true;
        }
        else {
            System.out.println("Port is open not open, try again!");
            }*/
        


	public TicTacToeClient(String ipAdd, int portNum) {
		try {
			socket = new Socket(ipAdd, portNum);
		} catch (UnknownHostException u) {
			System.out.println("found this");
			System.out.println(u);
		} catch (IOException i) {
			System.out.println("Port or IP Address does not exist: " +i);
		}
	}
	
	public static void player2Move(char[][] gameBoard) {
		System.out.println("Please make a move. (1-9)");

		int move = clientScanner.nextInt();

		boolean result = checkValidMove(move, gameBoard);

		while (!result) {
			System.out.println("Sorry! Invalid Move. Try again");
			move = clientScanner.nextInt();
			result = checkValidMove(move, gameBoard);
		}
		System.out.println("Player 2 moved at position " + move);
		updateBoard(move, 2, gameBoard);
		try {
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		//dataOutputStream.writeUTF("Please make a move. (1-9)");
		//dataOutputStream.flush();
		dataOutputStream.writeInt( move );
		dataOutputStream.flush();
		}
		catch(IOException x) {
			System.out.println("Unknown Error at Line 262 "+ x);
		}

	}
	
	public static void player1Move(char[][] gameBoard) {
		try {
			System.out.println("Waiting for Player 1 to make a move. (1-9)");
			// Inputs -Brandan
						InputStream inputStream = socket.getInputStream();
						DataInputStream dataInputStream = new DataInputStream(inputStream);
						int move = dataInputStream.readInt();
						System.out.println("Player 1 moved at position " + move);
						updateBoard(move, 1, gameBoard);
		} catch (IOException i) {
			System.out.println("Error " + i);
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
	
	
	public static boolean checkValidMove(int move, char[][] gameBoard) {

		switch (move) {
		case 1:
			if (gameBoard[0][0] == '_') {
				return true;
			} else {
				return false;
			}
		case 2:
			if (gameBoard[0][2] == '_') {
				return true;
			} else {
				return false;
			}
		case 3:
			if (gameBoard[0][4] == '_') {
				return true;
			} else {
				return false;
			}

		case 4:
			if (gameBoard[1][0] == '_') {
				return true;
			} else {
				return false;
			}
		case 5:
			if (gameBoard[1][2] == '_') {
				return true;
			} else {
				return false;
			}
		case 6:
			if (gameBoard[1][4] == '_') {
				return true;
			} else {
				return false;
			}
		case 7:
			if (gameBoard[2][0] == ' ') {
				return true;
			} else {
				return false;
			}
		case 8:
			if (gameBoard[2][2] == ' ') {
				return true;
			} else {
				return false;
			}
		case 9:
			if (gameBoard[2][4] == ' ') {
				return true;
			} else {
				return false;
			}

		default:
			return false;
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
	public static String checkPlayAgain(int n) {
		if(n==1) {
			return "Y";
		}
		else {
			return "N";
		}
	}
	
	public static void resetBoard(char[][] gameBoard) {
		gameBoard[0][0] = '_';
		gameBoard[0][2] = '_';
		gameBoard[0][4] = '_';
		gameBoard[1][0] = '_';
		gameBoard[1][2] = '_';
		gameBoard[1][4] = '_';
		gameBoard[2][0] = ' ';
		gameBoard[2][2] = ' ';
		gameBoard[2][4] = ' ';
	}
	
	public static boolean checkGameOver(char[][] gameBoard) {

		// Checks for Horizontal Win
		if (gameBoard[0][0] == 'X' && gameBoard[0][2] == 'X' && gameBoard[0][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[0][0] == 'O' && gameBoard[0][2] == 'O' && gameBoard[0][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}
		if (gameBoard[1][0] == 'X' && gameBoard[1][2] == 'X' && gameBoard[1][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[1][0] == 'O' && gameBoard[1][2] == 'O' && gameBoard[1][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}
		if (gameBoard[2][0] == 'X' && gameBoard[2][2] == 'X' && gameBoard[2][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[2][0] == 'O' && gameBoard[2][2] == 'O' && gameBoard[2][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		// Checks for Vertical Wins

		if (gameBoard[0][0] == 'X' && gameBoard[1][0] == 'X' && gameBoard[2][0] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[0][0] == 'O' && gameBoard[1][0] == 'O' && gameBoard[2][0] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		if (gameBoard[0][2] == 'X' && gameBoard[1][2] == 'X' && gameBoard[2][2] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[0][2] == 'O' && gameBoard[1][2] == 'O' && gameBoard[2][2] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		if (gameBoard[0][4] == 'X' && gameBoard[1][4] == 'X' && gameBoard[2][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[0][4] == 'O' && gameBoard[1][4] == 'O' && gameBoard[2][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		// Checks for Diagonal Wins
		if (gameBoard[0][0] == 'X' && gameBoard[1][2] == 'X' && gameBoard[2][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[0][0] == 'O' && gameBoard[1][2] == 'O' && gameBoard[2][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		if (gameBoard[2][0] == 'X' && gameBoard[1][2] == 'X' && gameBoard[0][4] == 'X') {
			System.out.println("Player 1 Wins");
			playerScore++;
			return true;
		}
		if (gameBoard[2][0] == 'O' && gameBoard[1][2] == 'O' && gameBoard[0][4] == 'O') {
			System.out.println("Player 2 Wins");
			player2Score++;
			return true;
		}

		if (gameBoard[0][0] != '_' && gameBoard[0][2] != '_' && gameBoard[0][4] != '_' && gameBoard[1][0] != '_'
				&& gameBoard[1][2] != '_' && gameBoard[1][4] != '_' && gameBoard[2][0] != ' ' && gameBoard[2][2] != ' '
				&& gameBoard[2][4] != ' ') {
			System.out.println("Its a tie");
			return true;
		}

		return false;
	}
	/*public static Socket portIsOpen(String ip, int port, int timeout) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), timeout);
            portExists=true;
            return socket;
        } catch (Exception ex) {
            portExists=false;
            return socket;
        }
    }*/
}