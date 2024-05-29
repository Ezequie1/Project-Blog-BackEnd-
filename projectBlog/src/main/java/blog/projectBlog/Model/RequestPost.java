package blog.projectBlog.Model;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestPost {

    @Size(min = 5, message = "O título deve conter no mínimo 5 caractéres!")
    private String title;

    @Size(min = 5, message = "O texto deve conter no mínimo 5 caractéres!")
    private String text;
}
