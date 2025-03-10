package org.example.domain.events;

import org.example.domain.entities.User;

public class UserCreatedEvent {
    private final User user;

    public UserCreatedEvent (User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
