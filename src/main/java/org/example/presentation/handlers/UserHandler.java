package org.example.presentation.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.application.usecases.CreateUserUseCase;
import org.example.domain.entities.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;


public class UserHandler implements HttpHandler {
    private final CreateUserUseCase createUserUseCase;

    public UserHandler(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            sendResponse(exchange, 405, "{ \"error\": \"Method Not Allowed\" }");
            return;
        }

        InputStream inputStream = exchange.getRequestBody();
        String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        try {
            JSONObject json = new JSONObject(requestBody);
            String username = json.optString("username", "").trim();
            String email = json.optString("email", "").trim();
            int age = json.optInt("age", -1);

            if (username.isEmpty() || email.isEmpty() || age < 0) {
                sendResponse(exchange, 400, "{ \"error\": \"Invalid data\" }");
                return;
            }

            User user = createUserUseCase.execute(username, email, age);
            JSONObject responseJson = new JSONObject();
            responseJson.put("username", user.getUsername());
            responseJson.put("email", user.getEmail());
            responseJson.put("age", user.getAge());

            sendResponse(exchange, 201, responseJson.toString());
        } catch (Exception e) {
            sendResponse(exchange, 400, "{ \"error\": \"" + e.getMessage() + "\" }");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
