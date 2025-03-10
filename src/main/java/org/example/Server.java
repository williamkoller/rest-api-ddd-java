package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.application.usecases.CreateUserUseCase;
import org.example.domain.events.UserCreatedEvent;
import org.example.infra.eventdispatcher.EventDispatcher;
import org.example.infra.repositories.InMemoryUserRepository;
import org.example.presentation.handlers.UserHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        InMemoryUserRepository userRepository = new InMemoryUserRepository();
        EventDispatcher<UserCreatedEvent> eventDispatcher = new EventDispatcher<>();

        eventDispatcher.subscribe(event -> System.out.println("Event: USer created with e-mail" + event.getUser().getEmail()));

        CreateUserUseCase createUserUseCase = new CreateUserUseCase(userRepository, eventDispatcher);
        server.createContext("/users", new UserHandler(createUserUseCase));

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }
}
