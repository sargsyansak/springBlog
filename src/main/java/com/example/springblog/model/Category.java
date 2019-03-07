package com.example.springblog.model;

import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "post")

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "category")
@ToString(exclude = "post")

public class Category {

    @ManyToMany(mappedBy = "categories")
    @Transient
    Set<Post> post = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;
    @Column
    private String name;
}
