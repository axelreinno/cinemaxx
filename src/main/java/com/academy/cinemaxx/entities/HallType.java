package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

@Data
@Entity
@Table(name = "hall_type")
@SQLDelete(sql = "UPDATE hall_type SET deleted = true WHERE id = ?")
public class HallType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String code;

    @Column(nullable = false, columnDefinition = "varchar(50)")
    private String type;
}
