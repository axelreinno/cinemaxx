package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

@Data
@Entity
@Table(name = "genre")
@SQLDelete(sql = "UPDATE genre SET deleted = true WHERE id = ?")
public class Genre extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String genre;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies;
}
