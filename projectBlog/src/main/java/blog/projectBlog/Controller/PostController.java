package blog.projectBlog.Controller;

import blog.projectBlog.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Posts")
public class PostController {

    @Autowired
    private PostService service;
}
