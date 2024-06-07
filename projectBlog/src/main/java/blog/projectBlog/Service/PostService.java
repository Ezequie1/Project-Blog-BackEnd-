package blog.projectBlog.Service;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository repository;

    public List<Post> getAll(){
        return repository.findAll();
    }

    public List<Post> getPostsContaining(String value) {
        return repository.findByTitleContainingOrTextContaining(value, value);
    }

    public Post createPost(RequestPost post) {
        return repository.save(
                new Post(
                    post.getTitle(),
                    post.getText(),
                    false
                )
        );
    }

    public Post editPost(Long id, RequestPost post) {
        if(!repository.existsById(id)) throw new EntityNotFoundException();

        Post postActual = repository.getReferenceById(id);

        postActual.setTitle(post.getTitle());
        postActual.setText(post.getText());

        return repository.save(postActual);
    }

    public Post setFavorite(Long id) {
        if(!repository.existsById(id)) throw new EntityNotFoundException();

        Post post = repository.getReferenceById(id);
        post.setFavorite(!post.isFavorite());
        return repository.save(post);
    }

    public String deletePost(Long id) {
        if(!repository.existsById(id)) throw new EntityNotFoundException();

        repository.deleteById(id);
        return "Deletado com sucesso!";
    }
}
