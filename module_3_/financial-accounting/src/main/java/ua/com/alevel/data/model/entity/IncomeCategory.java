package ua.com.alevel.data.model.entity;

import ua.com.alevel.data.annotation.Category;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "income_category")
@Category
public class IncomeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private IncomeCategoryType type;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "income_category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id", referencedColumnName = "id")
    )
    @Column(name = "income")
    Set<Operation> operations = new HashSet<>();

    public IncomeCategoryType getType() {
        return type;
    }

    public void setType(IncomeCategoryType type) {
        this.type = type;
    }

    public IncomeCategory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncomeCategory that)) return false;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
