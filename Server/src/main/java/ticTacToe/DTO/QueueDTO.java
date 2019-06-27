package ticTacToe.DTO;

public class QueueDTO {

	private UserDTO Player1;
	private UserDTO Player2;
	private int room;
	
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
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	
}
