package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Data
@Entity
@Table(name = "users", indexes = {
        @Index(name = "users_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;
}
