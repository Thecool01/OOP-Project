package oopproject.academic;

import oopproject.enums.LessonType;

import java.io.Serializable;

public class Lesson implements Serializable {
    private LessonType type;

    public Lesson() {
    }

    public Lesson(LessonType type) {
        this.type = type;
    }

    public LessonType getType() {
        return type;
    }

    public void setType(LessonType type) {
        this.type = type;
    }
}
