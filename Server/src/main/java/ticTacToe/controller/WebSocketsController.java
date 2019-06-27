package ticTacToe.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import ticTacToe.DTO.GameDTO;
import ticTacToe.DTO.QueueDTO;
import ticTacToe.DTO.UserDTO;
import ticTacToe.game.Game;

@Controller
public class WebSocketsController {
	private int randomNr;
	private int room = 0;
	
	@Autowired
	private Game game;
	private List<UserDTO> userList = new ArrayList<UserDTO>();
	private List<GameDTO> gameList = new ArrayList<GameDTO>();

	@CrossOrigin("*")
	@MessageMapping("/search")
	@SendTo("/topic/queue/")
	public QueueDTO queue(UserDTO userDto) throws Exception {
		if (userList.isEmpty()) {
			userList.add(userDto);
		} else {
			if (userList.get(0).getId() == userDto.getId()) {
				return null;
			} else {
				userList.add(userDto);
			}
		}
		if (userList.size() == 2) {
			QueueDTO queueDto = new QueueDTO();
			GameDTO gameDto=new GameDTO();
			
			randomNr = game.startPlayer(0, 1);
			if (randomNr == 0) {
				queueDto.setPlayer1(userList.get(0));
				queueDto.setPlayer2(userList.get(1));
				
			}
			else
			{
				queueDto.setPlayer1(userList.get(1));
				queueDto.setPlayer2(userList.get(0));
				
			}
			gameDto.setPlayer1(queueDto.getPlayer1());
			gameDto.setPlayer2(queueDto.getPlayer2());
			gameDto.setStatus(1);
			game.gameProgress(gameDto);
			gameList.add(gameDto);
			
			queueDto.setRoom(room);
			userList.clear();
			
			room++;
			return queueDto;
		}
		return null;
	}

	@CrossOrigin("*")
	@MessageMapping("/game/{roomId}")
	@SendTo("/topic/game/{roomId}")
	public GameDTO game(@DestinationVariable int roomId, GameDTO gameDto) throws Exception {
		gameList.get(roomId).setMove(gameDto.getMove());
		game.gameProgress(gameList.get(roomId));
		return gameList.get(roomId);
	}
}
