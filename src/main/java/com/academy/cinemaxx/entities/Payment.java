package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.PaymentMethod;
import com.academy.cinemaxx.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "payment", indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE payment SET deleted = true WHERE id = ?")
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(nullable = false)
    private LocalDateTime paymentTime;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

}
