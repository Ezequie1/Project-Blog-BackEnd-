package blog.projectBlog.Controller;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Posts")
@Validated
public class PostController {       //Acesse http://localhost:8080/swagger-ui/index.html para utilizar o Swagger

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<List<Post>> getPosts(){
        return ResponseEntity.status(200).body(service.getAll());
    }

    @PostMapping("/Create")
    public ResponseEntity createPost(@RequestBody @Valid RequestPost post){
        return ResponseEntity.status(201).body(service.createPost(post));
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity editPost(@PathVariable Long id, @RequestBody @Valid RequestPost post){
        return ResponseEntity.status(200).body(service.editPost(id, post));
    }
}
