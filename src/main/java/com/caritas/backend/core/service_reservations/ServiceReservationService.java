package com.caritas.backend.core.service_reservations;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationRequest;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationSerialized;
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

    public List<ServiceReservationSerialized> getAllServiceReservations() {
        return serviceReservationRepository.findAll()
                .stream()
                .map(serviceReservation -> new ServiceReservationSerialized(serviceReservation, serviceReservation.getReservation(), serviceReservation.getService()))
                .toList();
    }

    public ServiceReservationSerialized getServiceReservationById(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        return new ServiceReservationSerialized(serviceReservation, serviceReservation.getReservation(), serviceReservation.getService());
    }

    public ServiceReservationSerialized createServiceReservation(ServiceReservationRequest request) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(request.reservationId());
        ServiceEntity service = serviceRepository.findOneOrFail(request.serviceId());

        ServiceReservationEntity serviceReservation = new ServiceReservationEntity(reservation, service, request.orderDate(), request.costCount(), request.state(), request.externalReservationId());
        ServiceReservationEntity saved = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationSerialized(saved, saved.getReservation(), saved.getService());
    }

    public ServiceReservationSerialized updateServiceReservation(UUID id, ServiceReservationRequest request) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        serviceReservation.setOrderDate(request.orderDate());
        serviceReservation.setCostCount(request.costCount());
        serviceReservation.setState(request.state());
        ServiceReservationEntity updated = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationSerialized(updated, updated.getReservation(), updated.getService());
    }

    public void deleteServiceReservation(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        serviceReservation.detach();
        serviceReservationRepository.delete(serviceReservation);
    }
}
