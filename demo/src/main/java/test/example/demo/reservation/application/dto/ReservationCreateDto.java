package test.example.demo.reservation.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationCreateDto {

    private Long userId;
    private Long bedId;
    private LocalDateTime startingDate;
    private LocalDateTime finishingDate;
}
