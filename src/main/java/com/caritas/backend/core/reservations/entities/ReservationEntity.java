package com.caritas.backend.core.reservations.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.core.hostels.entities.HostelEntity;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.users.entities.UserEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostel_id", nullable = false)
    private HostelEntity hostel;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "active", columnDefinition = "boolean default true", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceInterestEntity> serviceInterests;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceReservationEntity> serviceReservations;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonReservationEntity> personReservations;

    public ReservationEntity() {
    }

    public ReservationEntity(UserEntity user, HostelEntity hostel, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.hostel = hostel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public HostelEntity getHostel() {
        return hostel;
    }

    public void detach() {
        if (this.user != null) {
            this.user.getReservations().remove(this);
            this.user = null;
        }
        if (this.hostel != null) {
            this.hostel.getReservations().remove(this);
            this.hostel = null;
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ServiceInterestEntity> getServiceInterests() {
        return serviceInterests;
    }

    public List<ServiceReservationEntity> getServiceReservations() {
        return serviceReservations;
    }

    public List<PersonReservationEntity> getPersonReservations() {
        return personReservations;
    }
}
