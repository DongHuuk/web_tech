package com.springdata.web.web_tech.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.springdata.web.web_tech.post.CommentSpec.isBest;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentRepositoryTest {

    @PersistenceContext
    EntityManager manager;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void spec(){

        final Page<Comment> all = commentRepository
                .findAll(isBest().and(CommentSpec.isGood()), PageRequest.of(0, 10));

    }

    @Test
    public void qbe(){
        Comment prove = new Comment();
        prove.setBest(true);

        ExampleMatcher.matching()
                .withIncludeNullValues();
    }

    @Test
    public void projectionTest(){
        final Comment comment = createComment();
        final Post post = createPost();
        comment.setPost(post);

        commentRepository.findByPost_Id(comment.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("===================");
            System.out.println(c.getVotes());
        });

    }

    @Test
    public void commentTest(){
        manager.clear();
        Comment comment = createComment();
        Post post = createPost();
        comment.setPost(post);

//        final List<Comment> all = commentRepository.findAll();
        final Optional<Comment> byId = commentRepository.getById(comment.getId());
//        final Optional<Comment> save = commentRepository.findById(comment.getId());
//        assertThat(save.isPresent() ? save.get().getPost().getTitle() : false).isEqualTo("Spring");

//        assertThat(comment.getPost().getTitle()).isEqualTo("Spring");
    }

    private Post createPost(){
        Post post = new Post();
        post.setId(1L);
        post.setTitle("spring");
        return postRepository.save(post);
    }

    private Comment createComment(){
        Comment comment = new Comment();
        comment.setComment("JPA");
        comment.setUp(1);
        comment.setDown(5);
        return commentRepository.save(comment);
    }

}