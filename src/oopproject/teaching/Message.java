package oopproject.teaching;

import oopproject.users.Employee;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private String messageId;
    private Employee sender;
    private Employee receiver;
    private String text;
    private LocalDateTime sentAt = LocalDateTime.now();
    private boolean read;

    public Message() {
    }

    public Message(String messageId, Employee sender, Employee receiver, String text) {
        this.messageId = messageId;
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public String getMessageId() {
        return messageId;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean read() {
        read = true;
        return read;
    }
}
