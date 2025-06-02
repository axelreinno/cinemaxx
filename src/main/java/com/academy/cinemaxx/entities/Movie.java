package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.AgeRating;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "movie", indexes = {
        @Index(name = "movie_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE movie SET deleted = true WHERE id = ?")
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "int default 0")
    private int durationMin;

    @Column(length = 512)
    private String trailerUrl;

    @Column(length = 512)
    private String thumbnailUrl;

    private LocalDate releaseDate;

    private String director;

    @Enumerated(EnumType.STRING)
    private AgeRating ageRating;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(int durationMin) {
        this.durationMin = durationMin;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

}
