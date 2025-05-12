package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "genre", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE genre SET deleted = true WHERE id = ?")
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
