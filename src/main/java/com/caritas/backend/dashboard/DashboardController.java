package com.caritas.backend.dashboard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.caritas.backend.common.ServiceNames;
import com.caritas.backend.core.reservations.entities.ReservationRepository;
import com.caritas.backend.core.reservations.entities.ReservationState;
import com.caritas.backend.core.service_reservations.entities.ServiceReservationRepository;
import com.caritas.backend.dashboard.dtos.ReservationsStateCountResponse;
import com.caritas.backend.dashboard.dtos.ServiceReservationsTypeCountResponse;
import com.caritas.backend.dashboard.dtos.YearlyHistogramResponse;

@RestController
@RequestMapping("/admin/dashboard")
public class DashboardController {
    private final ReservationRepository reservationRepository;
    private final ServiceReservationRepository serviceReservationRepository;

    public DashboardController(
            ReservationRepository reservationRepository,
            ServiceReservationRepository serviceReservationRepository) {
        this.reservationRepository = reservationRepository;
        this.serviceReservationRepository = serviceReservationRepository;
    }

    @GetMapping("/resevations-histogram")
    public YearlyHistogramResponse GetReservationsHistogram() {
        Integer[] frequencies = new Integer[12];
        Arrays.fill(frequencies, 0);

        List<Object[]> results = reservationRepository.countReservationsByMonth(2025);

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            frequencies[month - 1] = count.intValue();
        }

        return new YearlyHistogramResponse(frequencies);
    }

    @GetMapping("/persons-histogram")
    public YearlyHistogramResponse GetPersonsHistogram() {
        Integer[] frequencies = new Integer[12];
        Arrays.fill(frequencies, 0);

        List<Object[]> results = reservationRepository.countPersonsByMonth(2025);

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            Long count = (Long) result[1];
            frequencies[month - 1] = count.intValue();
        }

        return new YearlyHistogramResponse(frequencies);

    }

    @GetMapping("/reservations-state-count")
    public ReservationsStateCountResponse GetReservationsStateCount() {
        Integer[] pending = new Integer[12];
        Integer[] active = new Integer[12];
        Integer[] inactive = new Integer[12];
        Integer[] cancelled = new Integer[12];

        Arrays.fill(pending, 0);
        Arrays.fill(active, 0);
        Arrays.fill(inactive, 0);
        Arrays.fill(cancelled, 0);

        List<Object[]> results = reservationRepository.countReservationsByMonthAndState(2025);

        for (Object[] result : results) {
            Integer month = (Integer) result[0];
            ReservationState state = (ReservationState) result[1];
            Long count = (Long) result[2];

            switch (state) {
                case PENDING -> pending[month - 1] = count.intValue();
                case ACTIVE -> active[month - 1] = count.intValue();
                case INACTIVE -> inactive[month - 1] = count.intValue();
                case CANCELLED -> cancelled[month - 1] = count.intValue();
            }
        }

        return new ReservationsStateCountResponse(pending, active, inactive, cancelled);
    }

    @GetMapping("/service-reservations-type-count")
    public ServiceReservationsTypeCountResponse GetServiceReservationsTypeCount() {
        Map<String, Integer> counts = new HashMap<>();
        for (String type : ServiceNames.SERVICES) {
            counts.put(type, 0);
        }

        List<Object[]> results = serviceReservationRepository.countServiceReservationsByType(2025);

        for (Object[] result : results) {
            String type = (String) result[0];
            Long count = (Long) result[1];
            counts.put(type, count.intValue());
        }

        return new ServiceReservationsTypeCountResponse(
                counts.get(ServiceNames.TRANSPORTATION),
                counts.get(ServiceNames.BREAKFAST),
                counts.get(ServiceNames.MEAL),
                counts.get(ServiceNames.DINNER),
                counts.get(ServiceNames.LAUNDRY),
                counts.get(ServiceNames.BATH),
                counts.get(ServiceNames.DENTAL),
                counts.get(ServiceNames.MENTAL),
                counts.get(ServiceNames.DOCUMENT));
    }
}
