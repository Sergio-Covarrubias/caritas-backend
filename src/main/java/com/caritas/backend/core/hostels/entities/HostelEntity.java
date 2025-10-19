package com.caritas.backend.core.hostels.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.common.utils.TextUtils;
import com.caritas.backend.core.hostel_services.entities.HostelServiceEntity;
import com.caritas.backend.core.reservations.entities.ReservationEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "hostels")
public class HostelEntity {
    
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    @Column(name = "location_url", nullable = false)
    private String locationUrl;

    @Column(name = "image_urls", columnDefinition = "TEXT", nullable = false)
    private String imageUrls;

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "hostel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HostelServiceEntity> hostelServices = new ArrayList<>();

    public HostelEntity() {
    }

    public HostelEntity(String name, String description, Float price, Integer maxCapacity, String locationUrl, String[] imageUrls) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxCapacity = maxCapacity;
        this.locationUrl = locationUrl;
        setImageUrls(imageUrls);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String[] getImageUrls() {
        return TextUtils.StringToArray(imageUrls);
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = TextUtils.ArrayToString(imageUrls);
    }

    public List<ReservationEntity> getReservations() {
        return reservations;
    }

    public List<HostelServiceEntity> getHostelServices() {
        return hostelServices;
    }
}
