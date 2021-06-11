package ua.com.alevel.data.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Access(AccessType.PROPERTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private StudentGroup group;

    @OneToMany(mappedBy = "student")
    private Set<Assessment> assessments = new HashSet<>();

    public Student() {
        super();
    }

    public Student(Long id,String firstName, String lastName, int age, String email, StudentGroup group) {
        super(firstName, lastName, age, email);
        this.group = group;
        this.id=id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentGroup getGroup() {
        return group;
    }

    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    public Set<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(Assessment assessment) {
        this.assessments.add(assessment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
