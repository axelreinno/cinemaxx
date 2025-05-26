package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Data
@Entity
@Table(name = "cinema", indexes = {
        @Index(name = "cinema_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE cinema SET deleted = true WHERE id = ?")
public class Cinema extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
