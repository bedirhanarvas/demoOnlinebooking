package test.example.demo.reservation.api.command;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.reservation.application.dto.ReservationCreateDto;
import test.example.demo.reservation.application.dto.ReservationUpdateDto;
import test.example.demo.reservation.application.service.command.ReservationCommandService;

@RestController
@RequestMapping("/api/reservations/commands")
@RequiredArgsConstructor
public class ReservationCommandController {

    private final ReservationCommandService reservationCommandService;

    @PostMapping
    public ResponseEntity<Result> createReservation(@RequestBody @Valid ReservationCreateDto createDto) {
        Result result = reservationCommandService.createReservation(createDto);
        return buildResponse(result);
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<Result> updateReservation(@PathVariable Long reservationId,
                                                    @RequestBody @Valid ReservationUpdateDto dto) {
        Result result = reservationCommandService.updateReservation(reservationId, dto);
        return buildResponse(result);
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Result> deleteReservation(@PathVariable Long reservationId) {
        Result result = reservationCommandService.deleteReservation(reservationId);
        return buildResponse(result);
    }

    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Result> cancelReservation(@PathVariable Long reservationId) {
        Result result = reservationCommandService.cancelReservation(reservationId);
        return buildResponse(result);
    }

    private ResponseEntity<Result> buildResponse(Result result) {
        if (result.isSuccess()) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}

