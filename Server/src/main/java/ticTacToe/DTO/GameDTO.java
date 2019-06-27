package ticTacToe.DTO;

public class GameDTO {

	private UserDTO Player1;
	private UserDTO Player2;
	private int [][]matrix;
	private int status;
	private int move;
	private UserDTO PlayerWon;
	
	public UserDTO getPlayer1() {
		return Player1;
	}
	public void setPlayer1(UserDTO player1) {
		Player1 = player1;
	}
	public UserDTO getPlayer2() {
		return Player2;
	}
	public void setPlayer2(UserDTO player2) {
		Player2 = player2;
	}
	public int[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getMove() {
		return move;
	}
	public void setMove(int move) {
		this.move = move;
	}
	
	public void setMatrixElement(int i, int j, int value) {
		this.matrix[i][j]=value;
	}
	public int getMatrixElement(int i, int j)
	{
		return matrix[i][j];
	}
	public UserDTO getPlayerWon() {
		return PlayerWon;
	}
	public void setPlayerWon(UserDTO playerWon) {
		PlayerWon = playerWon;
	}
	
}
