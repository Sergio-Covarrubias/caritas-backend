package com.caritas.backend.dev;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.core.hostel_services.entities.HostelServiceRepository;
import com.caritas.backend.core.hostels.entities.HostelRepository;
import com.caritas.backend.core.person_reservations.entities.PersonReservationRepository;
import com.caritas.backend.core.persons.entities.PersonRepository;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationRepository;
import com.caritas.backend.core.services.entities.ServiceRepository;
import com.caritas.backend.core.users.entities.UserRepository;

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

    public DevController(UserRepository userRepository, PersonRepository personRepository, ServiceRepository serviceRepository,
            HostelRepository hostelRepository, HostelServiceRepository hostelServiceRepository, 
            ReservationRepository reservationRepository, PersonReservationRepository personReservationRepository,
            ServiceReservationRepository serviceReservationRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.serviceRepository = serviceRepository;
        this.hostelRepository = hostelRepository;
        this.hostelServiceRepository = hostelServiceRepository;
        this.reservationRepository = reservationRepository;
        this.personReservationRepository = personReservationRepository;
        this.serviceReservationRepository = serviceReservationRepository;
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
    }
}
