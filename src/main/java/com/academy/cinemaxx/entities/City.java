package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "city", indexes = {
        @Index(name = "city_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE city SET deleted = true WHERE id = ?")
public class City extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(100)")
    private String name;
}
