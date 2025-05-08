package test.example.demo.reservation.application.service.query;

import java.time.LocalDateTime;
import java.util.List;

import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.reservation.application.dto.ActiveReservationDto;

public interface ReservationQueryService {

    DataResult<ActiveReservationDto> getActiveReservationById(Long reservationId);

    DataResult<List<ActiveReservationDto>> getActiveReservationsByUserId(Long userId);

    DataResult<List<ActiveReservationDto>> getActiveReservationsByBedId(Long bedId);

    DataResult<List<ActiveReservationDto>> getAllActiveReservations(); // EK

    DataResult<List<ActiveReservationDto>> getReservationsOnDate(LocalDateTime date); // OPSİYONEL
}

