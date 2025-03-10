package org.example.application.usecases;

import org.example.domain.entities.User;
import org.example.domain.events.UserCreatedEvent;
import org.example.domain.repositories.UserRepository;
import org.example.infra.eventdispatcher.EventDispatcher;

public class CreateUserUseCase {
    private final UserRepository userRepository;
    private final EventDispatcher<UserCreatedEvent> eventDispatcher;

    public CreateUserUseCase(UserRepository userRepository, EventDispatcher<UserCreatedEvent> eventDispatcher) {
        this.userRepository = userRepository;
        this.eventDispatcher = eventDispatcher;
    }

    public User execute(String username, String email, int age) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("User already exists with this email.");
        }

        User user = new User(username, email, age);
        userRepository.save(user);
        eventDispatcher.dispatch(new UserCreatedEvent(user));

        return user;
    }
}
