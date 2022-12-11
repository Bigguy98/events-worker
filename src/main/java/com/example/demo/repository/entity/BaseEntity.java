package com.example.demo.repository.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "active")
    private Integer active;
}
