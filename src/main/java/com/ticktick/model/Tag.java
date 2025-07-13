package com.ticktick.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String color;

    private boolean haveChildren;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Tag parent;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
