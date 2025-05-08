package test.example.demo.user.application.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import test.example.demo.user.application.dto.ReservationSummaryDto;
import test.example.demo.user.application.dto.UpdateUserRequest;
import test.example.demo.user.application.dto.UserDetailDto;
import test.example.demo.user.domain.entity.User;

@Component
public class UserMapper {

	public void updateUserFromDto(User user, UpdateUserRequest dto) {
		if (dto.getEmail() != null) {
			user.setEmail(dto.getEmail());
		}

		if (dto.getPassword() != null) {
			user.setPassword(dto.getPassword());
		}

		if (dto.getPhoneNumber() != null) {
			user.setPhoneNumber(dto.getPhoneNumber());
		}

		if (dto.getAddress() != null) {
			user.setAddress(dto.getAddress());
		}

	}

	public UserDetailDto toUserdatilDto(User user) {
		List<ReservationSummaryDto> reservationDtos = user.getReservations().stream().map(
				reservation -> new ReservationSummaryDto(reservation.getStartingDate(), reservation.getFinishingDate()))
				.collect(Collectors.toList());

		return new UserDetailDto(user.getName(), user.getSurname(), user.getTcNumber(), user.getEmail(),
				reservationDtos);
	}

}