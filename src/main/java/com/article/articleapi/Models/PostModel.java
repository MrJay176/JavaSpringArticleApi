package com.article.articleapi.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table
public class PostModel {

    public PostModel() {

    }

    public Collection<FileDB> getImage() {
        return image;
    }

    public void setImage(Collection<FileDB> image) {
        this.image = image;
    }

    public PostModel(Long id, Collection<FileDB> image, String title, String description, String posterId) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
        this.posterId = posterId;
    }

    public PostModel(Collection<FileDB> image, String title, String description, String posterId) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.posterId = posterId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })

    private Collection<FileDB> image = new ArrayList<>();
    private String title;
    private String description;
    private String posterId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", image=" + image +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", posterId='" + posterId + '\'' +
                '}';
    }
}
