package test.example.demo.user.application.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationSummaryDto {
    private LocalDateTime startingDate;
    private LocalDateTime finishingDate;
}
