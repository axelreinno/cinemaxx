package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "hall", indexes = {
        @Index(name = "hall_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE hall SET deleted = true WHERE id = ?")
public class Hall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "hall_type_id", nullable = false)
    private HallType hallType;

    @Column(name = "row_total", nullable = false, columnDefinition = "int default 0")
    private Integer rowTotal;

    @Column(name = "column_total", nullable = false, columnDefinition = "int default 0")
    private Integer columnTotal;

    @OneToMany(mappedBy = "hall")
    private List<Seat> seats;

    @OneToMany(mappedBy = "hall")
    private List<Showtime> showtimes;

}
