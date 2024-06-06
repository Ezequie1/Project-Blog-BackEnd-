package blog.projectBlog.Repository;

import blog.projectBlog.Model.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class PostRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    PostRepository repository;

    @Test
    @DisplayName("Must return sucessful post from DB containing equals value to title or text")
    void findByTitleContainingOrTextContainingCase1() {
        Post post = new Post("Post title", "Post text", false);
        entityManager.persist(post);

        List<Post> result = repository.findByTitleContainingOrTextContaining("title", "text");

        assertEquals(post, result.get(0));
        assertFalse(result.isEmpty());
    }

    @Test
    @DisplayName("Must return an empty array from the DB when not have posts containing title or text equals to value")
    void findByTitleContainingOrTextContainingCase2() {
        Post post = new Post("Post title", "Post text", false);
        entityManager.persist(post);

        List<Post> result = repository.findByTitleContainingOrTextContaining("Random value", "Random value");
        List<Post> hasValues = repository.findByTitleContainingOrTextContaining("Post", "Post");

        assertTrue(result.isEmpty());
        assertEquals("[]", result.toString());

        assertEquals(post, hasValues.get(0));
        assertFalse(hasValues.isEmpty());
    }
}