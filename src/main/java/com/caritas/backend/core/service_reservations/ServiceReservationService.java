package com.caritas.backend.core.service_reservations;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.common.ServiceDispatcher;
import com.caritas.backend.common.utils.UtilsJSON;
import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_reservations.dtos.ServiceReservationSerialized;
import com.caritas.backend.core.service_reservations.dtos.UpdateServiceReservationRequest;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationEntity;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationRepository;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationState;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class ServiceReservationService {

    private final ServiceReservationRepository serviceReservationRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceRepository serviceRepository;
    private final ServiceDispatcher serviceDispatcher;

    public ServiceReservationService(ServiceReservationRepository serviceReservationRepository,
            ReservationRepository reservationRepository,
            ServiceRepository serviceRepository, ServiceDispatcher serviceDispatcher) {
        this.serviceReservationRepository = serviceReservationRepository;
        this.reservationRepository = reservationRepository;
        this.serviceRepository = serviceRepository;
        this.serviceDispatcher = serviceDispatcher;
    }

    public List<ServiceReservationSerialized> getAllServiceReservations() {
        return serviceReservationRepository.findAll()
                .stream()
                .map(serviceReservation -> new ServiceReservationSerialized(serviceReservation,
                        serviceReservation.getReservation(), serviceReservation.getService()))
                .toList();
    }

    public ServiceReservationSerialized getServiceReservationById(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        return new ServiceReservationSerialized(serviceReservation, serviceReservation.getReservation(),
                serviceReservation.getService());
    }

    public JsonNode getServiceReservationByIdWithDetails(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        return this.serviceDispatcher.getServiceReservation(serviceReservation.getService().getType(), serviceReservation.getExternalReservationId());
    }

    public ServiceReservationSerialized createServiceReservation(JsonNode request) {
        UUID reservationId = UUID.fromString(request.get("reservationId").asText());
        ReservationEntity reservation = this.reservationRepository.findOneOrFail(reservationId);

        String serviceName = UtilsJSON.getField("serviceName", request, true).asText();
        ServiceEntity service = this.serviceRepository.findByType(serviceName).orElseThrow();
        
        LocalDate orderDate = LocalDate.parse(UtilsJSON.getField("orderDate", request, true).asText());

        ServiceDispatcher.ServiceReservationCallResponse response = this.serviceDispatcher.createServiceReservation(request);
        
        ServiceReservationEntity serviceReservation = new ServiceReservationEntity(reservation, service, orderDate, response.count(), response.externalReservationId());
        ServiceReservationEntity saved = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationSerialized(saved, reservation, service);
    }

    public ServiceReservationSerialized confirmServiceReservation(UUID id) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

        serviceReservation.setState(ServiceReservationState.CONFIRMED);
        ServiceReservationEntity updated = serviceReservationRepository.save(serviceReservation);

        return new ServiceReservationSerialized(updated, updated.getReservation(), updated.getService());
    }

    public ServiceReservationSerialized updateServiceReservation(UUID id, UpdateServiceReservationRequest request) {
        ServiceReservationEntity serviceReservation = serviceReservationRepository.findOneOrFail(id);

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
