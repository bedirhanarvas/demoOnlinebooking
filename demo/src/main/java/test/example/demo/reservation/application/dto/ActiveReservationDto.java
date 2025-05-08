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
public class ActiveReservationDto {
	private String reservationNumber;
    private LocalDateTime startingDate;
    private LocalDateTime finishingDate;
    private Long bedId;
}
