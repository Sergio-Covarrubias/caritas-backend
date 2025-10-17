package com.caritas.backend.dev;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.hostel_services.entities.HostelServiceRepository;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.person_reservations.entities.PersonReservationRepository;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationRepository;
import com.caritas.backend.core.services.entities.ServiceRepository;
import com.caritas.backend.core.users.entities.UserEntity;
import com.caritas.backend.core.users.entities.UserRepository;
import com.caritas.backend.services.baths.BathReservationRepository;
import com.caritas.backend.services.breakfasts.BreakfastReservationRepository;
import com.caritas.backend.services.dentals.DentalReservationRepository;
import com.caritas.backend.services.dinners.DinnerReservationRepository;
import com.caritas.backend.services.documents.DocumentReservationRepository;
import com.caritas.backend.services.laundries.LaundryReservationRepository;
import com.caritas.backend.services.meals.MealReservationRepository;
import com.caritas.backend.services.mentals.MentalReservationRepository;
import com.caritas.backend.services.transportations.TransportationReservationRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/dev")
public class DevController {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final ServiceRepository serviceRepository;
    private final HostelRepository hostelRepository;
    private final HostelServiceRepository hostelServiceRepository;
    private final ReservationRepository reservationRepository;
    private final PersonReservationRepository personReservationRepository;
    private final ServiceReservationRepository serviceReservationRepository;

    private final BathReservationRepository bathReservationRepository;
    private final BreakfastReservationRepository breakfastReservationRepository;
    private final DentalReservationRepository dentalReservationRepository;
    private final DinnerReservationRepository dinnerReservationRepository;
    private final DocumentReservationRepository documentReservationRepository;
    private final LaundryReservationRepository laundryReservationRepository;
    private final MealReservationRepository mealReservationRepository;
    private final MentalReservationRepository mentalReservationRepository;
    private final TransportationReservationRepository transportationReservationRepository;

    public DevController(
            UserRepository userRepository,
            PersonRepository personRepository,
            ServiceRepository serviceRepository,
            HostelRepository hostelRepository,
            HostelServiceRepository hostelServiceRepository,
            ReservationRepository reservationRepository,
            PersonReservationRepository personReservationRepository,
            ServiceReservationRepository serviceReservationRepository,

            BathReservationRepository bathReservationRepository,
            BreakfastReservationRepository breakfastReservationRepository,
            DentalReservationRepository dentalReservationRepository,
            DinnerReservationRepository dinnerReservationRepository,
            DocumentReservationRepository documentReservationRepository,
            LaundryReservationRepository laundryReservationRepository,
            MealReservationRepository mealReservationRepository,
            MentalReservationRepository mentalReservationRepository,
            TransportationReservationRepository transportationReservationRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.serviceRepository = serviceRepository;
        this.hostelRepository = hostelRepository;
        this.hostelServiceRepository = hostelServiceRepository;
        this.reservationRepository = reservationRepository;
        this.personReservationRepository = personReservationRepository;
        this.serviceReservationRepository = serviceReservationRepository;

        this.bathReservationRepository = bathReservationRepository;
        this.breakfastReservationRepository = breakfastReservationRepository;
        this.dentalReservationRepository = dentalReservationRepository;
        this.dinnerReservationRepository = dinnerReservationRepository;
        this.documentReservationRepository = documentReservationRepository;
        this.laundryReservationRepository = laundryReservationRepository;
        this.mealReservationRepository = mealReservationRepository;
        this.mentalReservationRepository = mentalReservationRepository;
        this.transportationReservationRepository = transportationReservationRepository;
    }

    @PostMapping("/wipe")
    public void wipeData() {
        serviceReservationRepository.deleteAll();
        personReservationRepository.deleteAll();
        reservationRepository.deleteAll();
        hostelServiceRepository.deleteAll();
        hostelRepository.deleteAll();
        serviceRepository.deleteAll();
        personRepository.deleteAll();
        userRepository.deleteAll();

        bathReservationRepository.deleteAll();
        breakfastReservationRepository.deleteAll();
        dentalReservationRepository.deleteAll();
        dinnerReservationRepository.deleteAll();
        documentReservationRepository.deleteAll();
        laundryReservationRepository.deleteAll();
        mealReservationRepository.deleteAll();
        mentalReservationRepository.deleteAll();
        transportationReservationRepository.deleteAll();
    }


    private record LoginRequest(String phoneNumber) {
    }
    private record LoginResponse(String userId) {
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        UserEntity user = this.userRepository.findByPhoneNumber(request.phoneNumber).get();
        return new LoginResponse(user.getId());
    }

}
