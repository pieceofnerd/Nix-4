package ua.com.alevel.data.model.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Column(name = "course_name")
    private String courseName;

    @Column(name = "hours_number", nullable = false)
    @Min(1)
    @Max(180)
    private int hoursNumber;

    @OneToMany(mappedBy = "courses")
    private Set<Topic> topics = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<StudentGroup> groups = new HashSet<>();

    @ManyToMany(mappedBy = "courses")
    private Set<Teacher> teachers = new HashSet<>();

    public Course() {
    }

    public Course(Long id, String courseName, int hoursNumber) {
        this.id = id;
        this.courseName = courseName;
        this.hoursNumber = hoursNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getHoursNumber() {
        return hoursNumber;
    }

    public void setHoursNumber(int hoursNumber) {
        this.hoursNumber = hoursNumber;
    }

    public Set<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Topic topic) {
        this.topics.add(topic);
    }

    public Set<StudentGroup> getGroups() {
        return groups;
    }

    public void setGroups(StudentGroup group) {
        this.groups.add(group);
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher teacher) {
        this.teachers.add(teacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
