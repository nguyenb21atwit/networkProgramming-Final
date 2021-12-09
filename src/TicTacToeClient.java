import java.net.*;
import java.io.*;
import java.util.Scanner;

public class TicTacToeClient {

	private static Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	static int playerScore = 0;
	static int player2Score = 0;
	// Geeks for Geeks, Socket Programming in Java
	// https://www.geeksforgeeks.org/socket-programming-in-java/
	static Scanner clientScanner = new Scanner(System.in);

	public static void main(String[] args) {
		char[][] gameBoard = { { '_', '|', '_', '|', '_' }, { '_', '|', '_', '|', '_' }, { ' ', '|', ' ', '|', ' ' } };
		boolean gameOver = false;
		boolean playAgain = true;
		System.out.print("Please enter IP Address or 'localhost': ");
		String ipAddress = clientScanner.nextLine();
		// Creates a new Socket to connect to Host
		TicTacToeClient clientSocket = new TicTacToeClient(ipAddress, clientScanner);
		System.out.println("Connected to server!");
		printBoard(gameBoard);
		System.out.println("Welcome to Tic Tac Toe!!");
		// Plays game, calling turns until the game is over and host ends
		while (playAgain) {
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
				// Receives int from Host to determine replay/end game
				InputStream inputStream = socket.getInputStream();
				DataInputStream dataInputStream = new DataInputStream(inputStream);
				int replayHost = dataInputStream.readInt();
				// Passes int to change to String cases
				String result = checkPlayAgain(replayHost);
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
					System.out.println("Host Ended Session. Game Over!");
					break;
				default:
					break;
				}
			} catch (IOException z) {
				System.out.println("Unknown Error " + z);
			}
		}
	}

	public TicTacToeClient(String ipAdd, Scanner clientScanner) {
		try {
			System.out.print("Please enter port: ");
			int port = clientScanner.nextInt();
			socket = new Socket(ipAdd, port);
		} catch (UnknownHostException u) {
			System.out.println("IP Address does not exist, restart client, try again");
			System.exit(0);
		} catch (IOException i) {
			System.out.println("Port does not exist, try again");
			TicTacToeClient clientSocket = new TicTacToeClient(ipAdd, clientScanner);
		}
	}

	public static void player2Move(char[][] gameBoard) {
		//Player 2's move
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
			// Outputs to the Host - Player 2's move
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeInt(move);
			dataOutputStream.flush();
		} catch (IOException x) {
			System.out.println("Unknown Error at Line 262 " + x);
		}
	}

	public static void player1Move(char[][] gameBoard) {
		try {
			//Player 1's moves
			System.out.println("Waiting for Player 1 to make a move. (1-9)");
			// Inputs from the Host - Player 1's moves
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
		if (n == 1) {
			return "Y";
		} else {
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
}