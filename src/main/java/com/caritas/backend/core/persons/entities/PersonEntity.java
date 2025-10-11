package com.caritas.backend.core.persons.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.caritas.backend.common.TextUtils;
import com.caritas.backend.core.person_reservations.entities.PersonReservationEntity;
import com.caritas.backend.core.users.entities.UserEntity;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class PersonEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "alergies", columnDefinition = "TEXT", nullable = false)
    private String alergies;

    @Column(name = "discapacities", columnDefinition = "TEXT", nullable = false)
    private String discapacities;

    @Column(name = "medicines", columnDefinition = "TEXT", nullable = false)
    private String medicines;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PersonReservationEntity> personReservations = new ArrayList<>();

    public PersonEntity() {
    }

    public PersonEntity(UserEntity user, String firstName, String lastName, LocalDate birthdate, String[] alergies,
            String[] discapacities,
            String[] medicines) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        setAlergies(alergies);
        setDiscapacities(discapacities);
        setMedicines(medicines);
    }

    public UUID getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthdate) {
        this.birthDate = birthdate;
    }

    public String[] getAlergies() {
        return TextUtils.StringToArray(alergies);
    }

    public void setAlergies(String[] alergies) {
        this.alergies = TextUtils.ArrayToString(alergies);
    }

    public String[] getDiscapacities() {
        return TextUtils.StringToArray(discapacities);
    }

    public void setDiscapacities(String[] discapacities) {
        this.discapacities = TextUtils.ArrayToString(discapacities);
    }

    public String[] getMedicines() {
        return TextUtils.StringToArray(medicines);
    }

    public void setMedicines(String[] medicines) {
        this.medicines = TextUtils.ArrayToString(medicines);
    }

    public List<PersonReservationEntity> getPersonReservations() {
        return personReservations;
    }

    public void detach() {
        if (this.user != null) {
            this.user.getPersons().remove(this);
            this.user = null;
        }
    }
}
