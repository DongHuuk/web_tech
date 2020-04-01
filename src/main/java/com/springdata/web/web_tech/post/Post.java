package com.springdata.web.web_tech.post;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    private Long id;

    private String title;
    @Temporal(TemporalType.TIMESTAMP)
    private Date create;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreate() {
        return create;
    }

    public void setCreate(Date create) {
        this.create = create;
    }
}
