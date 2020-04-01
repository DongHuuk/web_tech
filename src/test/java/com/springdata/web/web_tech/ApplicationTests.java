package com.springdata.web.web_tech;

import com.springdata.web.web_tech.post.Post;
import com.springdata.web.web_tech.post.PostRepository;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @PersistenceContext
    EntityManager entityManager;

    final  String test_1 = "Test_1";

    @Test
    public void findByTitleWithStart(){
        Post post = new Post();
        post.setId(1L);
        post.setTitle("Spring Data Api");
        postRepository.save(post);

        final List<Post> spring = postRepository.findByTitleStartingWith("Spring");
        assertThat(spring.isEmpty()).isFalse();

    }

    @Test
    public void findByTitleNamed(){
        Post spring = savePost();

        final List<Post> springList = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));
        assertThat(springList.size()).isEqualTo(1);

        spring.setTitle("hibernate");

        final List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("hibernate");

    }

    @Test
    public void updateTest(){
        Post post = new Post();
        post.setId(2L);
        post.setTitle("Spring");
        Post save = postRepository.save(post);

        save.setTitle("hibernate");

        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("hibernate");

    }

    private Post savePost() {
        Post post = new Post();
        post.setId(2L);
        post.setTitle("Spring");
        return postRepository.save(post);
    }


    @Test
    public void crud(){
        Post post = new Post();
        post.setId(1L);
        post.setTitle("jpa");
        postRepository.save(post);

        Post postUpdate = new Post();
        postUpdate.setId(1L);
        postUpdate.setTitle("hibernate");
        postRepository.save(postUpdate);

        final List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void contextLoads() throws Exception {

        Post post = new Post();
        post.setTitle("title");
        post.setCreate(new Date());
        postRepository.save(post);

        mockMvc.perform(get("/posts/" + post.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("title"));



    }
    @Test
    void postpageTest() throws Exception {

        createPosts();

        mockMvc.perform(get("/posts/")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "create,desc")
                    .param("sort", "title"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title", is("title")));
    }

    private void createPosts(){
        long postsCount = 100L;
        while (postsCount > 0){
            Post post = new Post();
            post.setId(postsCount);
            post.setTitle("title" + postsCount);
            post.setCreate(new Date());
            postRepository.save(post);
            postsCount--;
        }
    }

}
