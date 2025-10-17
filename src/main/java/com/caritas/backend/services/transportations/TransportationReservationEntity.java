package com.caritas.backend.services.transportations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "transportation_reservations")
public class TransportationReservationEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "hostel_name", nullable = false)
    private String hostelName;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "from_hostel", nullable = false)
    private Boolean fromHostel;

    @Column(name = "pickup_time", nullable = false)
    private LocalTime pickupTime;

    public TransportationReservationEntity() {
    }

    public TransportationReservationEntity(LocalDate orderDate, Integer count, String hostelName, String place, Boolean fromHostel, LocalTime pickupTime) {
        this.orderDate = orderDate;
        this.count = count;
        this.hostelName = hostelName;
        this.place = place;
        this.fromHostel = fromHostel;
        this.pickupTime = pickupTime;
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

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Boolean getFromHostel() {
        return fromHostel;
    }

    public void setFromHostel(Boolean fromHostel) {
        this.fromHostel = fromHostel;
    }

    public LocalTime getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(LocalTime pickupTime) {
        this.pickupTime = pickupTime;
    }
}
