package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "movie_rating", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE movie_rating SET deleted = true WHERE id = ?")
public class MovieRating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(columnDefinition = "text")
    private String review;

    @Column(precision = 2, scale = 1)
    private BigDecimal rating;
}
