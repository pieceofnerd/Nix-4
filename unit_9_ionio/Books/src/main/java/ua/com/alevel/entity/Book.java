package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
public abstract class Book extends BaseEntity {

    private String title;
    private Set<String> authorsId;
    private BookType bookType;

    public Book(String title) {
        this.title = title;
        authorsId = new HashSet<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(authorsId, book.authorsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authorsId);
    }
}
