package ticTacToe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ticTacToe.entity"})  //Scan JPA entities (and is necesary if you want to generate the tables automatic and the entities are not in the same package as the main class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
}
