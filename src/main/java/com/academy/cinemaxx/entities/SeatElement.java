package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "seat_element")
@SQLDelete(sql = "UPDATE seat_element SET deleted = true WHERE id = ?")
public class SeatElement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(15)")
    private String code;

    @Column(nullable = false)
    private String element;

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

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }
}
