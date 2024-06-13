package blog.projectBlog.Controller;

import blog.projectBlog.Model.Post;
import blog.projectBlog.Model.RequestPost;
import blog.projectBlog.Service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @MockBean
    PostService service;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Must be return one array with 2 Posts equals to preset")
    void getPostsCase1() throws Exception {
        List<Post> posts = List.of(
                new Post("Title for first post", "Text for first post", false),
                new Post("Title for second post", "Text for second post", false)
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        when(service.getAll(pageable)).thenReturn(postPage);

        mockMvc.perform(
                get("/Posts")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content", hasSize(2)))
                    .andExpect(content().json(objectMapper.writeValueAsString(postPage)
                )
        ).andReturn().getResponse().getContentAsString();

        verify(service, times(1)).getAll(pageable);
    }

    @Test
    @DisplayName("Must be return a empty array when database is empty")
    void getPostsCase2() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(), pageable, 0);

        when(service.getAll(pageable)).thenReturn(postPage);

        mockMvc.perform(
                get("/Posts")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.content", hasSize(0)
                )
        );

        verify(service, times(1)).getAll(pageable);
    }

    @Test
    @DisplayName("Must be return 2 Posts with title and text containing value 'for'")
    void getPostsContainingCase1() throws Exception {
        List<Post> posts = List.of(
                new Post("Title for first post", "Text for first post", false),
                new Post("Title for second post", "Text for second post", false)
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(posts, pageable, posts.size());

        when(service.getPostsContaining("for", pageable)).thenReturn(postPage);

        mockMvc.perform(
            get("/Posts/Search/{value}", "for")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title", is(posts.get(0).getTitle())))
                .andExpect(jsonPath("$.content[0].text", is(posts.get(0).getText())))
                .andExpect(jsonPath("$.content[1].title", is(posts.get(1).getTitle())))
                .andExpect(jsonPath("$.content[1].text", is(posts.get(1).getText())))
                .andExpect(content().json(objectMapper.writeValueAsString(postPage)
            )
        ).andReturn().getResponse().getContentAsString();

        verify(service, times(1)).getPostsContaining("for", pageable);
    }

    @Test
    @DisplayName("Must be return an empty array when title and text not containing value 'Random value'")
    void getPostsContainingCase2() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(List.of(), pageable, 0);

        when(service.getPostsContaining("Random value", pageable)).thenReturn(postPage);

        mockMvc.perform(
                get("/Posts/Search/{value}", "Random value")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.content", hasSize(0))
                );

        verify(service, times(1)).getPostsContaining("Random value", pageable);
    }

    @Test
    @DisplayName("Must be return a post created when the request respected all rules of creation")
    void createPostCase1() throws Exception{
        RequestPost requestPost = new RequestPost("Title post", "Text post");
        Post post = new Post("Title post", "Text post", true);

        when(service.createPost(requestPost)).thenReturn(post);

        mockMvc.perform(post("/Posts/Create")
                                .contentType(MediaType.APPLICATION_JSON).content("{\"title\":\"Title post\",\"text\":\"Text post\"}"))
                                .andExpect(status().isCreated())
                                .andExpect(content().json(objectMapper.writeValueAsString(post))
        );

        verify(service, times(1)).createPost(requestPost);
    }

    @Test
    @DisplayName("Must be return 400 status when request not respected size rules for title in creation")
    void createPostCase2() throws Exception{
        RequestPost requestPost = new RequestPost("Tit", "Text post");

        Exception error = mockMvc.perform(post("/Posts/Create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPost)))
                        .andExpect(status().isBadRequest()
        ).andReturn().getResolvedException();

        assertTrue(error instanceof MethodArgumentNotValidException);

        ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().forEach(e -> {
            assertEquals("O título deve conter no mínimo 5 caractéres!", e.getDefaultMessage());
            assertEquals("title", e.getField());
            assertEquals("Tit", e.getRejectedValue());
        });
    }

    @Test
    @DisplayName("Must be return 400 status when request not respected size rules for text in creation")
    void createPostCase3() throws Exception{
        RequestPost requestPost = new RequestPost("Title post", "Tex");

        Exception error = mockMvc.perform(post("/Posts/Create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPost)))
                        .andExpect(status().isBadRequest()
        ).andReturn().getResolvedException();

        assertTrue(error instanceof MethodArgumentNotValidException);

        ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().forEach(e -> {
            assertEquals("O texto deve conter no mínimo 5 caractéres!", e.getDefaultMessage());
            assertEquals("text", e.getField());
            assertEquals("Tex", e.getRejectedValue());
        });
    }

    @Test
    @DisplayName("Must be return a status 200 and post edited with title and text changed")
    void editPostCase1() throws Exception {
        Post post = new Post("New title of post", "New text of post", false);

        when(service.editPost(1L, new RequestPost("New title of post", "New text of post"))).thenReturn(post);

        mockMvc.perform(put("/Posts/Edit/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new RequestPost("New title of post", "New text of post"))))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(post))
        );

        verify(service, times(1)).editPost(1L, new RequestPost("New title of post", "New text of post"));
    }

    @Test
    @DisplayName("Must be return a status 400 when request not respect title size rules for editing")
    void editPostCase2() throws Exception {
        Exception error =mockMvc.perform(put("/Posts/Edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RequestPost("Pos", "Post text"))))
                        .andExpect(status().isBadRequest()
        ).andReturn().getResolvedException();

        assertTrue(error instanceof MethodArgumentNotValidException);

        ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().forEach(e -> {
            assertEquals("O título deve conter no mínimo 5 caractéres!", e.getDefaultMessage());
            assertEquals("title", e.getField());
            assertEquals("Pos", e.getRejectedValue());
        });
    }

    @Test
    @DisplayName("Must be return a status 400 when request not respect text size rules for editing")
    void editPostCase3() throws Exception {
        Exception error = mockMvc.perform(put("/Posts/Edit/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RequestPost("Post title", "Pos"))))
                        .andExpect(status().isBadRequest()
        ).andReturn().getResolvedException();

        assertTrue(error instanceof MethodArgumentNotValidException);

        ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().forEach(e -> {
            assertEquals("O texto deve conter no mínimo 5 caractéres!", e.getDefaultMessage());
            assertEquals("text", e.getField());
            assertEquals("Pos", e.getRejectedValue());
        });
    }

    @Test
    @DisplayName("Must be return a post with favorite equals true")
    void changeFavoriteCase1() throws Exception {
        Post post = new Post("Title post", "Text post", true);

        when(service.setFavorite(1L)).thenReturn(post);

        mockMvc.perform(put("/Posts/Favorite/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(post))
        );

        verify(service, times(1)).setFavorite(1L);
    }

    @Test
    @DisplayName("Must be return a post with favorite equals false")
    void changeFavoriteCase2() throws Exception {
        Post post = new Post("Title post", "Text post", false);

        when(service.setFavorite(1L)).thenReturn(post);

        mockMvc.perform(put("/Posts/Favorite/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(post))
        );

        verify(service, times(1)).setFavorite(1L);
    }

    @Test
    @DisplayName("Must be return the default text when the post was deleted")
    void deletePost() throws Exception {
        when(service.deletePost(1L)).thenReturn("Deletado com sucesso!");

        String response = mockMvc.perform(delete("/Posts/Delete/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()
        ).andReturn().getResponse().getContentAsString();

        verify(service, times(1)).deletePost(1L);

        assertEquals("Deletado com sucesso!", response);
    }
}