package blog.projectBlog.Controller;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Service.PostService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/Search/{value}")
    public ResponseEntity<List<Post>> getPostsContaining(@PathVariable String value){
        return ResponseEntity.status(200).body(service.getPostsContaining(value));
    }

    @PostMapping("/Create")
    public ResponseEntity<Post> createPost(@RequestBody @Valid RequestPost post){
        return ResponseEntity.status(201).body(service.createPost(post));
    }

    @PutMapping("/Edit/{id}")
    public ResponseEntity<Post> editPost(@PathVariable Long id, @RequestBody @Valid RequestPost post){
        return ResponseEntity.status(200).body(service.editPost(id, post));
    }

    @PutMapping("/Favorite/{id}")
    public ResponseEntity<Post> changeFavorite(@PathVariable Long id) {
        return ResponseEntity.status(200).body(service.setFavorite(id));
    }

    @DeleteMapping("/Delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        return ResponseEntity.status(200).body(service.deletePost(id));
    }
}
