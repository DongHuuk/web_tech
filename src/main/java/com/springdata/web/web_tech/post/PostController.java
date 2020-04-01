package com.springdata.web.web_tech.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id) {
        final Optional<Post> byId = postRepository.findById(id);
        final Post post = byId.get();
        return post.getTitle();
    }

    @GetMapping("/posts")
    public PagedModel<EntityModel<Post>> postPage(Pageable pageable, PagedResourcesAssembler<Post> assembler){
        createPosts();
        Pageable pageable1 = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"id", "title"));

        return assembler.toModel(postRepository.findAll(pageable1));
    }

    private void createPosts(){
        int postsCount = 100;
        while (postsCount > 0){
            Post post = new Post();
            post.setTitle("title" + postsCount);
            post.setCreate(new Date());
            postRepository.save(post);
            postsCount--;
        }
    }
}
