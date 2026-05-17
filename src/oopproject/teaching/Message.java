package oopproject.teaching;

import oopproject.users.Employee;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String messageId;
    private Employee sender;
    private Employee receiver;
    private String text;
    private final LocalDateTime sentAt = LocalDateTime.now();
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

    // marks message as read
    public void read() {
        this.read = true;
    }

    public boolean isRead() {
        return read;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;

        if (messageId == null || message.messageId == null) {
            return false;
        }

        return Objects.equals(messageId, message.messageId);
    }

    @Override
    public int hashCode() {
        return messageId == null ? System.identityHashCode(this) : Objects.hash(messageId);
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", read=" + read +
                '}';
    }
}
