package blog.projectBlog.Repository;

import blog.projectBlog.Model.Post;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;


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

        Page<Post> result = repository.findByTitleContainingOrTextContaining("title", "text", PageRequest.of(0, 10));

        assertEquals(post, result.getContent().get(0));
        assertFalse(result.getContent().isEmpty());
    }

    @Test
    @DisplayName("Must return an empty array from the DB when not have posts containing title or text equals to value")
    void findByTitleContainingOrTextContainingCase2() {
        Post post = new Post("Post title", "Post text", false);
        entityManager.persist(post);

        Pageable pageable = PageRequest.of(0, 10);

        Page<Post> result = repository.findByTitleContainingOrTextContaining("Random value", "Random value", pageable);
        Page<Post> hasValues = repository.findByTitleContainingOrTextContaining("Post", "Post", pageable);

        assertTrue(result.getContent().isEmpty());
        assertEquals("[]", result.getContent().toString());

        assertEquals(post, hasValues.getContent().get(0));
        assertFalse(hasValues.getContent().isEmpty());
    }
}