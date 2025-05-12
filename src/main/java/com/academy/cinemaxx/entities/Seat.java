package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@Table(name = "seat", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE seat SET deleted = true WHERE id = ?")
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "seat_element_id", nullable = false)
    private SeatElement seatElement;

    @Column(name = "row_index", nullable = false, columnDefinition = "int default 0")
    private Integer row;

    @Column(name = "column_index", nullable = false, columnDefinition = "int default 0")
    private Integer column;

    @Column(nullable = false)
    private String label;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;
}
