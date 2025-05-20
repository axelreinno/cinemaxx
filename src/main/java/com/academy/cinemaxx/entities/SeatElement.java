package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Data
@Entity
@Table(name = "seat_element", indexes = {
        @Index(name = "seat_element_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE seat_element SET deleted = true WHERE id = ?")
public class SeatElement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String element;
}
