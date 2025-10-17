package com.caritas.backend.services.mentals;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "mental_reservations")
public class MentalReservationEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "count", nullable = false)
    private Integer count;

    public MentalReservationEntity() {
    }

    public MentalReservationEntity(LocalDate orderDate, Integer count) {
        this.orderDate = orderDate;
        this.count = count;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
