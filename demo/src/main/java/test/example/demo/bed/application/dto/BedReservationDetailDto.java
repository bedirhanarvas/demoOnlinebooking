package test.example.demo.bed.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.example.demo.user.application.dto.UserDetailDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BedReservationDetailDto {
    private Long bedId;
    private String bedNumber;
    private boolean isAvailable;
    private UserDetailDto userDetail;
}