package org.example.domain.entities;

public class User {
    private final String username;
    private final String email;
    private final int age;

    public User(String username, String email, int age) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }

        this.username = username;
        this.email = email;
        this.age = age;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
