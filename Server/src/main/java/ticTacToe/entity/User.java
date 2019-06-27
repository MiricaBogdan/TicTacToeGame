package ticTacToe.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String name;
	private int gameswon;
	
	public User() {
		
	}
	public User(String username, String password, String name, int gameswon) {
		this.username = username;
		this.password = password;
		this.name = name;
		this.gameswon=gameswon;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGameswon() {
		return gameswon;
	}
	public void setGameswon(int gameswon) {
		this.gameswon = gameswon;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", gameswon="
				+ gameswon + "]";
	}
	
	
}
