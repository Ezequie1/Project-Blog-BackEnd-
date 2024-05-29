package blog.projectBlog.Repository;

import blog.projectBlog.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContainingOrTextContaining(String title, String text);
}
