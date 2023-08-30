package com.blog.app.apis.entityes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "Posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postId;
    @NotBlank
    @Size(min = 5,max = 50)
    private  String title;
    @Column(length = 10000)
    private String content;
    @NotBlank(message = "imageName details")
    private String imageName;

    private Date addedDate;
    @ManyToOne

    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Comment> comments=new HashSet<>();

}
