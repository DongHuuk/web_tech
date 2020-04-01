package com.springdata.web.web_tech.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    /*
        value = "Comment.post" -> Named로 정의한 값을 사용할 때
        attributePaths = "post" -> Post Entity의 post 필드값을 찾아서 사용 attributePaths = "{String ...}"
     */
    @EntityGraph(attributePaths = "post")
    Optional<Comment> getById(Long id);

    <T> List<T> findByPost_Id(Long id, Class<T> type);

}
