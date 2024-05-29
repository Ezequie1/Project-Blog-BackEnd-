package blog.projectBlog.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    private boolean favorite;
    private LocalDate data_post;

    public Post(String title, String text, boolean favorite) {
        this.title = title;
        this.text = text;
        this.favorite = favorite;
        this.data_post = LocalDate.now();
    }

    public Post() {
    }
}
