package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Data
@Entity
@Table(name = "booking", indexes = {
        @Index(name = "booking_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE booking SET deleted = true WHERE id = ?")
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
}
