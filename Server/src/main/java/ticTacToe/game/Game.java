package ticTacToe.game;


import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ticTacToe.DTO.GameDTO;
import ticTacToe.entity.User;
import ticTacToe.repository.UserRepository;

@Component
public class Game {
	@Autowired
	private UserRepository userRepository;
	
	int i;
	int j;

	public int[][] StartMatrix() {
		int[][] matrix = new int[3][3];
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++) {
				matrix[i][j] = -1;
			}
		return matrix;
	}
	
	public int startPlayer(int min,int max)
	{
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public boolean checkIfPlayerWon(int[][] matrix, int value) {
		if ((matrix[0][0] == value) && (matrix[0][1] == value) && (matrix[0][2] == value)) {
			return true;
		}
		if ((matrix[1][0] == value) && (matrix[1][1] == value) && (matrix[1][2] == value)) {
			return true;
		}

		if ((matrix[2][0] == value) && (matrix[2][1] == value) && (matrix[2][2] == value)) {
			return true;
		}

		if ((matrix[0][0] == value) && (matrix[1][1] == value) && (matrix[2][2] == value)) {
			return true;
		}

		if ((matrix[0][2] == value) && (matrix[1][1] == value) && (matrix[2][0] == value)) {
			return true;
		}

		if ((matrix[0][0] == value) && (matrix[1][0] == value) && (matrix[2][0] == value)) {
			return true;
		}

		if ((matrix[0][1] == value) && (matrix[1][1] == value) && (matrix[2][1] == value)) {
			return true;
		}

		if ((matrix[0][2] == value) && (matrix[1][2] == value) && (matrix[2][2] == value)) {
			return true;
		}
		return false;
	}
	
	
	public boolean checkIfDrawGame(int[][] matrix)
	{
		for (i = 0; i < 3; i++)
			for (j = 0; j < 3; j++) {
				if(matrix[i][j] == -1)
				{
					return false;
				}
			}
		return true;
	}

	public GameDTO gameProgress(GameDTO gameDto) {
		switch (gameDto.getStatus()) {
		case 1:
			gameDto.setMatrix(StartMatrix());
			gameDto.setStatus(2);
			return gameDto;
			
		case 2:
			j=gameDto.getMove()%10;
			i=gameDto.getMove()/10;
			gameDto.setMatrixElement(i, j, 1);
			
			if(checkIfPlayerWon(gameDto.getMatrix(),1)==true)
			{
				User user=userRepository.findById(gameDto.getPlayer1().getId()).get();
				user.setGameswon(user.getGameswon()+1);
				userRepository.save(user);
				gameDto.setPlayerWon(gameDto.getPlayer1());
				System.out.println(gameDto.getPlayer1().getName());
				gameDto.setStatus(4);
			}
			else if(checkIfDrawGame(gameDto.getMatrix())==true)
			{
				gameDto.setStatus(4);
			}
			else
			{
				gameDto.setStatus(3);
			}
			return gameDto;
			
		case 3:
			j=gameDto.getMove()%10;
			i=gameDto.getMove()/10;
			gameDto.setMatrixElement(i, j, 0);
			
			if(checkIfPlayerWon(gameDto.getMatrix(),0)==true)
			{
				User user=userRepository.findById(gameDto.getPlayer2().getId()).get();
				user.setGameswon(user.getGameswon()+1);
				userRepository.save(user);
				gameDto.setPlayerWon(gameDto.getPlayer2());
				gameDto.setStatus(4);
			}
			// I believe there is no need for this statement because the draw game can only be decided on the first player turn
			/*else if(checkIfDrawGame(gameDto.getMatrix())==true)
			{
				gameDto.setStatus(4);
			}*/
			else
			{
				gameDto.setStatus(2);
			}
			return gameDto;
		
		case 4:
			return gameDto;
		}
		return null;
	}
}
