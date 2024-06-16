package blog.projectBlog.Service;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.function.Executable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository repository;

    @InjectMocks
    private PostService service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Must be return all posts DB")
    void getAllCase1() {
        Post first = new Post("First post", "PostText", false);
        Post second = new Post("Second post", "PostText", false);
        List<Post> posts = List.of(first, second);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        when(repository.findAll(pageable)).thenReturn(postPage);

        Page<Post> result = service.getAll(pageable);

        verify(repository, times(1)).findAll(pageable);

        assertEquals(2, result.getContent().size());
        assertSame(first, result.getContent().get(0));
        assertSame(second, result.getContent().get(1));
    }

    @Test
    @DisplayName("Must be return an empty array when DB is empty")
    void getAllCase2() {
        when(repository.findAll()).thenReturn(List.of());

        List<Post> result = repository.findAll();

        verify(repository, times(1)).findAll();

        assertTrue(result.isEmpty());
        assertEquals("[]", result.toString());
    }

    @Test
    @DisplayName("Must be return an array with a size containing title or text equal to the value")
    void getPostsContainingCase1() {
        Post post = new Post("Second post", "PostText", false);
        List<Post> posts = List.of(post);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        when(repository.findByTitleContainingOrTextContaining("Second", "Second", pageable)).thenReturn(postPage);

        Page<Post> result = repository.findByTitleContainingOrTextContaining("Second", "Second", pageable);

        verify(repository, times(1)).findByTitleContainingOrTextContaining("Second", "Second", pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getContent().size());
        assertSame(post, posts.get(0));
    }

    @Test
    @DisplayName("Must be return an empty array when the DB not have one post with title or text equal to value")
    void getPostsContainingCase2() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(), pageable, 0);
        when(repository.findByTitleContainingOrTextContaining("Value", "Value", pageable)).thenReturn(postPage);

        Page<Post> posts = service.getPostsContaining("Value", pageable);

        verify(repository, times(1)).findByTitleContainingOrTextContaining("Value", "Value", pageable);

        assertTrue(posts.isEmpty());
        assertEquals("[]", posts.getContent().toString());
    }

    @Test
    @DisplayName("Must be return a post created")
    void createPostCase() {
        Post post = new Post("Title post", "Text post", false);

        when(repository.save(any(Post.class))).thenReturn(post);

        Post result = service.createPost(new RequestPost("Title post", "Text post"));

        verify(repository, times(1)).save(any(Post.class));

        assertSame(post, result);
    }

    @Test
    @DisplayName("Must be return a post with title and text edited")
    void editPostCase1() {
        String oldTitle = "Title post";
        String oldText = "Text post";
        Post oldPost = new Post(oldTitle, oldText, false);

        when(repository.getReferenceById(oldPost.getId())).thenReturn(oldPost);
        when(repository.existsById(oldPost.getId())).thenReturn(true);
        when(repository.save(oldPost)).thenReturn(oldPost);

        Post result = service.editPost(oldPost.getId(), new RequestPost("Title post edited!", "Text post edited!"));

        verify(repository, times(1)).getReferenceById(oldPost.getId());
        verify(repository, times(1)).save(oldPost);

        assertNotEquals(oldTitle, oldPost.getTitle());
        assertNotEquals(oldText, oldPost.getText());
        assertEquals("Title post edited!", oldPost.getTitle());
        assertEquals("Text post edited!", oldPost.getText());
        assertSame(oldPost, result);
    }

    @Test
    @DisplayName("Must be return a EntityNotFoundException when post with id passed its not was found in editPost endpoint")
    void editPostCase2() {
        Post oldPost = new Post("Title post", "Text post", false);

        when(repository.existsById(oldPost.getId())).thenReturn(false);

        Executable executable = () -> service.editPost(oldPost.getId(), new RequestPost("Title post edited!", "Text post edited!"));

        assertThrows(EntityNotFoundException.class, executable);

        verify(repository, times(1)).existsById(oldPost.getId());
    }

    @Test
    @DisplayName("Must be return a post with a boolean value changed for true")
    void setFavoriteCase1() {
        Post oldPost = new Post("Title post", "Text post", false);

        when(repository.getReferenceById(1L)).thenReturn(oldPost);
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(oldPost)).thenReturn(oldPost);

        Post result = service.setFavorite(1L);

        verify(repository, times(1)).getReferenceById(1L);
        verify(repository, times(1)).save(oldPost);

        assertSame(oldPost, result);
        assertNotEquals(false, result.isFavorite());
        assertTrue(result.isFavorite());
    }

    @Test
    @DisplayName("Must be return a post with a boolean value changed for false")
    void setFavoriteCase2() {
        Post oldPost = new Post("Title post", "Text post", true);

        when(repository.getReferenceById(1L)).thenReturn(oldPost);
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(oldPost)).thenReturn(oldPost);

        Post result = service.setFavorite(1L);

        verify(repository, times(1)).getReferenceById(1L);
        verify(repository, times(1)).save(oldPost);

        assertSame(oldPost, result);
        assertNotEquals(true, result.isFavorite());
        assertFalse(result.isFavorite());
    }

    @Test
    @DisplayName("Must be return a EntityNotFoundException when post with id passed its not was found in setFavorite endpoint")
    void setFavoriteCase3() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.setFavorite(1L));

        verify(repository, times(1)).existsById(1L);
    }

    @Test
    @DisplayName("Must be return a text 'Deletado com sucesso!'")
    void deletePostCase1() {
        when(repository.existsById(1L)).thenReturn(true);

        assertEquals("Deletado com sucesso!", service.deletePost(1L));

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Must be return a EntityNotFoundException when post with id passed its not was found in deletePost endpoint")
    void deletePostCase2() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.deletePost(1L));

        verify(repository, times(1)).existsById(1L);
    }
}