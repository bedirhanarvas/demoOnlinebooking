package test.example.demo.user.application.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDto {
	
    private String name;
    private String surName;
    private String tcNumber;
    private String email;
    private List<ReservationSummaryDto> reservations;
    

}
