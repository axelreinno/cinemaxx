package com.academy.cinemaxx.entities;

import com.academy.cinemaxx.enums.SeatStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

import java.util.UUID;

@Entity
@Table(name = "seat", indexes = {
        @Index(name = "seat_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE seat SET deleted = true WHERE id = ?")
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecureId() {
        return secureId;
    }

    public void setSecureId(String secureId) {
        this.secureId = secureId;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public SeatElement getSeatElement() {
        return seatElement;
    }

    public void setSeatElement(SeatElement seatElement) {
        this.seatElement = seatElement;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}
