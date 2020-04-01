package com.springdata.web.web_tech.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleStartingWith(String title);

    @Query("select p from #{#entityName} p where p.title = :titleName")
    List<Post> findByTitle(@Param("titleName") String keyword, Sort sort);

    @Modifying
    @Query("UPDATE #{#entityName} p SET p.title = :titleName WHERE p.id = :idNumber")
    int updateTitle(@Param("titleName") String title, @Param("idNumber") Long id);

}
