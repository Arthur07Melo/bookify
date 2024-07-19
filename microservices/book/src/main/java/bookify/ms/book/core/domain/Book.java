package bookify.ms.book.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String id;
    private String title;
    private float price;
    private String gender;
    private String author;
    private String contentPath;
    
    public Book(String title, float price, String gender, String author, String contentPath) {
        this.title = title;
        this.price = price;
        this.gender = gender;
        this.author = author;
        this.contentPath = contentPath;
    }
}
