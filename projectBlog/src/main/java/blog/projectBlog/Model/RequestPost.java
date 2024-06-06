package blog.projectBlog.Model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPost {

    @Size(min = 5, message = "O título deve conter no mínimo 5 caractéres!")
    private String title;

    @Size(min = 5, message = "O texto deve conter no mínimo 5 caractéres!")
    private String text;
}
