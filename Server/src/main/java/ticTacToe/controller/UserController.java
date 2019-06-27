package ticTacToe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticTacToe.DTO.UserDTO;
import ticTacToe.entity.User;
import ticTacToe.repository.UserRepository;

@RestController  
@RequestMapping(path="/user") 
public class UserController {
	@Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
	private UserRepository userRepository;
	
	@PostMapping(path="/add", consumes = "application/json")
	public boolean addNewUser(@RequestBody User user) {
		if(userRepository.findByUsername(user.getUsername()).isEmpty())
		{
			user.setGameswon(0);
			userRepository.save(user);
			return true;
		}
		return false;
	}
	
	@PostMapping(path="/login", consumes = "application/json")
	public UserDTO login(@RequestBody User user){
		if(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword())==null) {
			return null;
		}
		UserDTO userDTO=new UserDTO();
		userDTO.setId(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).getId());
		userDTO.setName(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).getName());
		userDTO.setGameswon(userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).getGameswon());
		return userDTO;
		
	}
	@GetMapping(path="/all")
	public Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}
