package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.AgeRating;
import com.academy.cinemaxx.enums.MovieStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "movie", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id = ?")
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "int default 0")
    private int durationMin;

    private LocalDate releaseDate;

    private String director;

    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @OneToMany(mappedBy = "movie")
    private List<MovieRating> ratings;

    @ManyToMany
    @JoinTable(name = "movie_genre",
            joinColumns = {
                @JoinColumn(name = "movie_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "genre_id", referencedColumnName = "id")
            }
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;
}
