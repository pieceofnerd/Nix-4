package ua.com.alevel.data.model.entity;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lesson_start", nullable = false)
    private Instant lessonStart;

    @Column(name = "lesson_end", nullable = false)
    private Instant lessonEnd;

    @ManyToMany(mappedBy = "lessons")
    private Set<Topic> topics = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Lesson() {
    }



    public Lesson(Long id, Instant lessonStart, Instant lessonEnd) {
        this.id = id;
        this.lessonStart = lessonStart;
        this.lessonEnd = lessonEnd;
    }

    public Instant getLessonStart() {
        return lessonStart;
    }

    public void setLessonStart(Instant lessonStart) {
        this.lessonStart = lessonStart;
    }

    public Instant getLessonEnd() {
        return lessonEnd;
    }

    public void setLessonEnd(Instant lessonEnd) {
        this.lessonEnd = lessonEnd;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lesson lesson)) return false;
        return id.equals(lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
