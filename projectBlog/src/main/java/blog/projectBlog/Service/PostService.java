package blog.projectBlog.Service;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Repository.PostRepository;
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
}
