package test.example.demo.reservation.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationUpdateDto {

	private Long userId;
    private Long reservationId;
    private Long newBedId;
    private LocalDateTime newStartDate;
    private LocalDateTime newEndDate;
	
}
