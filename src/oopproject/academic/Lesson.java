package oopproject.academic;

import oopproject.enums.LessonType;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Lesson implements Serializable {
    private String lessonId;
    private String topic;
    private LessonType type;
    private LocalDateTime dateTime;
    private String room;

    public Lesson() {
    }

    public Lesson(LessonType type) {
        this.type = type;
    }

    public Lesson(String lessonId, String topic, LessonType type, LocalDateTime dateTime, String room) {
        this.lessonId = lessonId;
        this.topic = topic;
        this.type = type;
        this.dateTime = dateTime;
        this.room = room;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void reschedule(LocalDateTime dateTime, String room) {
        this.dateTime = dateTime;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId='" + lessonId + '\'' +
                ", topic='" + topic + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", room='" + room + '\'' +
                '}';
    }
}
