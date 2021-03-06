package ticTacToe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ticTacToe.entity.User;

//This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
//CRUD refers Create, Read, Update, Delete
public interface UserRepository extends CrudRepository<User, Integer>  {
	List<User> findByUsername(String username);
	User findByUsernameAndPassword(String username,String password);
}
