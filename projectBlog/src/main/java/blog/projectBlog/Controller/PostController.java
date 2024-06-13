package blog.projectBlog.Controller;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Posts")
@Validated
public class PostController {       //Acesse http://localhost:8080/swagger-ui/index.html para utilizar o Swagger

    @Autowired
    private PostService service;

    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(service.getAll(pageable));
    }

    @GetMapping("/Search/{value}")
    public ResponseEntity<Page<Post>> getPostsContaining(@PathVariable String value,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(200).body(service.getPostsContaining(value, pageable));
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
