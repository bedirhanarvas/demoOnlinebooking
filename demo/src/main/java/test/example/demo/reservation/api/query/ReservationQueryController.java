package test.example.demo.reservation.api.query;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.reservation.application.dto.ActiveReservationDto;
import test.example.demo.reservation.application.service.query.ReservationQueryService;

@RestController
@RequestMapping("/api/reservations/queries")
@RequiredArgsConstructor
public class ReservationQueryController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping("/{reservationId}")
    public ResponseEntity<DataResult<ActiveReservationDto>> getActiveReservationById(@PathVariable Long reservationId) {
        DataResult<ActiveReservationDto> result = reservationQueryService.getActiveReservationById(reservationId);
        return buildResponse(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResult<List<ActiveReservationDto>>> getActiveReservationsByUserId(@PathVariable Long userId) {
        DataResult<List<ActiveReservationDto>> result = reservationQueryService.getActiveReservationsByUserId(userId);
        return buildResponse(result);
    }

    @GetMapping("/bed/{bedId}")
    public ResponseEntity<DataResult<List<ActiveReservationDto>>> getActiveReservationsByBedId(@PathVariable Long bedId) {
        DataResult<List<ActiveReservationDto>> result = reservationQueryService.getActiveReservationsByBedId(bedId);
        return buildResponse(result);
    }

    @GetMapping("/all")
    public ResponseEntity<DataResult<List<ActiveReservationDto>>> getAllActiveReservations() {
        DataResult<List<ActiveReservationDto>> result = reservationQueryService.getAllActiveReservations();
        return buildResponse(result);
    }

    @GetMapping("/date")
    public ResponseEntity<DataResult<List<ActiveReservationDto>>> getReservationsOnDate(@RequestParam LocalDateTime date) {
        DataResult<List<ActiveReservationDto>> result = reservationQueryService.getReservationsOnDate(date);
        return buildResponse(result);
    }

    private <T> ResponseEntity<DataResult<T>> buildResponse(DataResult<T> result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

