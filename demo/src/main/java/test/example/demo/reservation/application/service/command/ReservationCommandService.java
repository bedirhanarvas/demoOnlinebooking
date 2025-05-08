package test.example.demo.reservation.application.service.command;

import test.example.demo.core.utilities.results.Result;
import test.example.demo.reservation.application.dto.ReservationCreateDto;
import test.example.demo.reservation.application.dto.ReservationUpdateDto;

public interface ReservationCommandService {

	Result createReservation(ReservationCreateDto createDto);
	
	Result cancelReservation(Long reservationId);
	
	Result updateReservation(Long reservationId, ReservationUpdateDto dto);
	
	Result deleteReservation(Long reservationId);
}
