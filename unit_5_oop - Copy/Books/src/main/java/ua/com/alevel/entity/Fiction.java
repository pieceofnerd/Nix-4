package ua.com.alevel.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Fiction extends Book {
    private String genre;

    public Fiction(String title) {
        super(title);
        setBookType(BookType.FICTION);
        this.genre = "field not set";
    }

    public Fiction(String id, String title, String genre) {
        super(title);
        this.setId(id);
        setBookType(BookType.FICTION);
        this.genre = "field not set";
    }


}
