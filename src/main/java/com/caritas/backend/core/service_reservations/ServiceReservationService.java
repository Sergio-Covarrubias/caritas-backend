package com.caritas.backend.core.service_reservations;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationRequest;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationResponse;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationRepository;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;

@Service
public class ServiceReservationService {

    private final ServiceReservationRepository serviceReservationRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceRepository serviceRepository;

    public ServiceReservationService(ServiceReservationRepository serviceReservationRepository,
            ReservationRepository reservationRepository,
            ServiceRepository serviceRepository) {
        this.serviceReservationRepository = serviceReservationRepository;
        this.reservationRepository = reservationRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceReservationResponse> getAllServiceReservations() {
        return serviceReservationRepository.findAll()
                .stream()
                .map(serviceReservation -> new ServiceReservationResponse(serviceReservation))
                .toList();
    }

    public ServiceReservationResponse getServiceReservationById(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        return new ServiceReservationResponse(serviceReservation);
    }

    public ServiceReservationResponse createServiceReservation(ServiceReservationRequest request) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(request.reservationId());
        ServiceEntity service = serviceRepository.findOneOrFail(request.serviceId());

        ServiceReservationEntity serviceReservation = new ServiceReservationEntity(reservation, service, request.externalReservationId());
        ServiceReservationEntity saved = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationResponse(saved);
    }

    public ServiceReservationResponse updateServiceReservation(UUID id, ServiceReservationRequest request) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        ServiceReservationEntity updated = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationResponse(updated);
    }

    public void deleteServiceReservation(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        serviceReservation.detach();
        serviceReservationRepository.delete(serviceReservation);
    }
}
