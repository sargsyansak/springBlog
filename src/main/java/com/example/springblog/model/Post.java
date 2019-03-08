package com.example.springblog.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
@ToString(exclude = "categories")


public class Post {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "post_category", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Category> categories = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String title;
    @Column(columnDefinition = "text")
    private String text;
    @Column
    private Date date;
    @Column(name = "pic_url")
    private String picUrl;


}
