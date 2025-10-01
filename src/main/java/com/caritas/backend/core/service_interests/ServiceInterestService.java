package com.caritas.backend.core.service_interests;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.caritas.backend.core.reservations.entities.ReservationEntity;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.service_interests.dtos.ServiceInterestRequest;
import com.caritas.backend.core.service_interests.dtos.ServiceInterestResponse;
import com.caritas.backend.core.service_interests.entities.ServiceInterestEntity;
import com.caritas.backend.core.service_interests.entities.ServiceInterestRepository;
import com.caritas.backend.core.services.entities.ServiceEntity;
import com.caritas.backend.core.services.entities.ServiceRepository;

@Service
public class ServiceInterestService {

    private final ServiceInterestRepository serviceInterestRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceRepository serviceRepository;

    public ServiceInterestService(ServiceInterestRepository serviceInterestRepository, ReservationRepository reservationRepository,
            ServiceRepository serviceRepository) {
        this.serviceInterestRepository = serviceInterestRepository;
        this.reservationRepository = reservationRepository;
        this.serviceRepository = serviceRepository;
    }

    public List<ServiceInterestResponse> getAllServiceInterests() {
        return serviceInterestRepository.findAll()
                .stream()
                .map(serviceInterest -> new ServiceInterestResponse(serviceInterest))
                .toList();
    }

    public ServiceInterestResponse getServiceInterestById(UUID id) {
        ServiceInterestEntity serviceInterest = serviceInterestRepository.findOneOrFail(id);

        return new ServiceInterestResponse(serviceInterest);
    }

    public ServiceInterestResponse createServiceInterest(ServiceInterestRequest request) {
        ReservationEntity reservation = reservationRepository.findOneOrFail(request.reservationId());
        ServiceEntity service = serviceRepository.findOneOrFail(request.serviceId());

        ServiceInterestEntity serviceInterest = new ServiceInterestEntity(reservation, service);
        ServiceInterestEntity saved = serviceInterestRepository.save(serviceInterest);

        return new ServiceInterestResponse(saved, reservation.getId(), service.getId());
    }

    public ServiceInterestResponse updateServiceInterest(UUID id, ServiceInterestRequest request) {
        ServiceInterestEntity serviceInterest = serviceInterestRepository.findOneOrFail(id);

        ServiceInterestEntity updated = serviceInterestRepository.save(serviceInterest);

        return new ServiceInterestResponse(updated);
    }

    public void deleteServiceInterest(UUID id) {
        ServiceInterestEntity serviceInterest = serviceInterestRepository.findOneOrFail(id);

        serviceInterest.detach();
        serviceInterestRepository.deleteById(id);
    }
}
