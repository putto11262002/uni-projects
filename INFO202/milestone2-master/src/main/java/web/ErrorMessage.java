package web;

import java.util.ArrayList;
import java.util.Collection;

public class ErrorMessage {

    private Collection<String> messages;

    public ErrorMessage() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

}