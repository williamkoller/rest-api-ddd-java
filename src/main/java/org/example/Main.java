package org.example;

import org.example.application.usecases.CreateUserUseCase;
import org.example.domain.events.UserCreatedEvent;
import org.example.infra.eventdispatcher.EventDispatcher;
import org.example.infra.repositories.InMemoryUserRepository;
import org.example.presentation.controllers.UserController;

public class Main {
    public static void main(String[] args) {

        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        EventDispatcher<UserCreatedEvent> eventDispatcher = new EventDispatcher<>();
        eventDispatcher.subscribe(event -> System.out.println("Event: user created with email" + event.getUser().getEmail()));

        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userRepository, eventDispatcher);
        UserController userController = new UserController(createUserUseCase);

        userController.createUser();
    }
}