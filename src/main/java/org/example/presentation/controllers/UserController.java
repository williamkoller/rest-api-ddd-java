package org.example.presentation.controllers;

import org.example.application.usecases.CreateUserUseCase;
import org.example.domain.entities.User;

import java.util.Scanner;

public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public void createUser() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            User user = createUserUseCase.execute(username, email, age);

            System.out.println("User created successfully!");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Age: " + user.getAge());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
