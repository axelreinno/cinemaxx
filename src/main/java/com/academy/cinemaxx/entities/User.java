package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "user", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<MovieRating> ratings;
}
