import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream ;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import java.net.*;
import java.io.OutputStream;

public class TicTacToeServer {
	// Tic Tac Toe in Java Tutorial -
	// https://medium.com/codex/tic-tac-toe-e53212028341
	static int playerScore = 0;
	static int player2Score = 0;
	static Scanner input = new Scanner(System.in);
	// Geeks for Geeks - Socket Programming in Java -
	// https://www.geeksforgeeks.org/socket-programming-in-java/
	private static Socket socket = null;
	private ServerSocket serverConnection = null;
	private DataInputStream in = null;
	private DataInputStream out = null;
	private PrintStream sendOut = null;

	public static void main(String[] args) {
		char[][] gameBoard = { { '_', '|', '_', '|', '_' }, { '_', '|', '_', '|', '_' }, { ' ', '|', ' ', '|', ' ' } };
		boolean gameOver = false;
		boolean playAgain = true;
		boolean singlePlayer = true;
		System.out.println("Single Player or Two-Player? Enter '1' for Single Player, '2' for Two Player");
		String gameMode = input.nextLine();
		switch (gameMode) {
		case "1":
		case "One":
			singlePlayer = true;
			System.out.println("Single Player Selected");
			break;
		case "2":
		case "Two":
			singlePlayer = false;
			System.out.println("Two Player Selected");
			break;
		}
		if (singlePlayer) {
			printBoard(gameBoard);
			System.out.println("Welcome to Tic Tac Toe!!");
			while (playAgain) {
				while (!gameOver) {
					playerMove(gameBoard);
					gameOver = checkGameOver(gameBoard);
					if (gameOver) {
						break;
					}
					// Add a check here for if Player 2 Disconnects, to have CPU play instead
					computerMove(gameBoard);
					gameOver = checkGameOver(gameBoard);
					if (gameOver) {
						break;
					}
				}
				System.out.println("Player 1 Score: " + playerScore);
				System.out.println("Player 2 Score: " + player2Score);
				System.out.println("Would you like to play again? Enter 'Y' for Yes, 'N' for No.");
				input.nextLine();
				String result = input.nextLine();

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
		} else {
			System.out.print("Please enter the Port Number: ");
			int port = input.nextInt();
			TicTacToeServer serverSocket = new TicTacToeServer(port);
			printBoard(gameBoard);
			
			System.out.println("Welcome to Tic Tac Toe!!");
			while (playAgain) {
				while (!gameOver) {
					playerMove(gameBoard);
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
				System.out.println("Would you like to play again? Enter 'Y' for Yes, 'N' for No.");
				input.nextLine();
				String result = input.nextLine();

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

			/*
			 * System.out.println("Player 1 Score: " + playerScore);
			 * System.out.println("Player 2 Score: " + player2Score); System.out.
			 * println("Would you like to play again? Enter 'Y' for Yes, 'N' for No.");
			 * input.nextLine(); String result = input.nextLine();
			 * 
			 * switch (result) { case "Y": case "y": playAgain = true;
			 * System.out.println("Playing again"); resetBoard(gameBoard); gameOver = false;
			 * printBoard(gameBoard); break;
			 * 
			 * case "N": case "n": playAgain = false; System.out.println("Game Over!");
			 * break; default: break; }
			 */

		}
	}

	public TicTacToeServer(int portNum) {
		try {
			serverConnection = new ServerSocket(portNum);
			System.out.println("Starting Server...");
			System.out.println("Waiting for a Player 2.");
			socket = serverConnection.accept();
			System.out.println("Player 2 found!");
			/*
			 * InputStream inputStream = socket.getInputStream(); DataInputStream
			 * dataInputStream = new DataInputStream(inputStream); String message =
			 * dataInputStream.readUTF(); System.out.println("Client Message: "+message);
			 */
		} catch (IOException i) {
			System.out.println("Not found " + i);
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

	/*
	 * public static String charToString(char[][] Board) { String s = ""; for (int
	 * i=0; i < Board.length; i++) { for (int j=0; j < Board[i].length; j++) { s +=
	 * Board[i][j]; } } return s.toString(); }
	 */

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

	public static void playerMove(char[][] gameBoard) {

		System.out.println("Please make a move. (1-9)");

		int move = input.nextInt();

		boolean result = checkValidMove(move, gameBoard);

		while (!result) {
			System.out.println("Sorry! Invalid Move. Try again");
			move = input.nextInt();
			result = checkValidMove(move, gameBoard);
		}

		System.out.println("Player 1 moved at position " + move);
		updateBoard(move, 1, gameBoard);

	}

	// Program method player2Move to take input from Client and send to Server
	// Also program to send gameBoard to Client as well
	public static void player2Move(char[][] gameBoard) {
		try {
			System.out.println("Waiting for Player 2 to make a move. (1-9)");
			// SendStringOverJava -
			// https://gist.github.com/chatton/8955d2f96f58f6082bde14e7c33f69a6
			// Outputs -Brandan
			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			
			// use objectoutput to send array? - thomas
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			
		    os.writeObject( gameBoard );
			
			
			dataOutputStream.writeUTF("Please make a move. (1-9)");
			dataOutputStream.flush();
			// Inputs -Brandan
			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			int intFromClient = dataInputStream.readInt();
			System.out.println("Player 2 Move: " + intFromClient);
			int move = intFromClient;

			boolean result = checkValidMove(move, gameBoard);

			while (!result) {
				System.out.println("Sorry! Invalid Move. Try again");
				dataOutputStream.writeUTF("Sorry! Invalid Move. Try again");
				dataOutputStream.flush();
				move = input.nextInt();
				result = checkValidMove(move, gameBoard);
			}

			System.out.println("Player 2 moved at position " + move);
			updateBoard(move, 2, gameBoard);
		} catch (IOException i) {
			System.out.println("Error " + i);
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

	public static void computerMove(char[][] gameBoard) {

		Random rand = new Random();
		int move = rand.nextInt(9) + 1;

		boolean result = checkValidMove(move, gameBoard);

		while (!result) {
			move = rand.nextInt(9) + 1;
			result = checkValidMove(move, gameBoard);
		}

		System.out.println("Computer moved at position " + move);
		updateBoard(move, 2, gameBoard);
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
}
