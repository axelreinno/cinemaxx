package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
