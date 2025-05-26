package com.academy.cinemaxx.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hall", indexes = {
        @Index(name = "hall_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE hall SET deleted = true WHERE id = ?")
public class Hall extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public HallType getHallType() {
        return hallType;
    }

    public void setHallType(HallType hallType) {
        this.hallType = hallType;
    }

    public Integer getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(Integer rowTotal) {
        this.rowTotal = rowTotal;
    }

    public Integer getColumnTotal() {
        return columnTotal;
    }

    public void setColumnTotal(Integer columnTotal) {
        this.columnTotal = columnTotal;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Showtime> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(List<Showtime> showtimes) {
        this.showtimes = showtimes;
    }
}
