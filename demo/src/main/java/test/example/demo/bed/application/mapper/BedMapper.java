package test.example.demo.bed.application.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import test.example.demo.bed.application.dto.BedDto;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.reservation.dataaccess.repository.ReservationRepository;
import test.example.demo.reservation.domain.entity.Reservation;
import test.example.demo.user.application.dto.UserDetailDto;
import test.example.demo.user.application.mapper.UserMapper;
import test.example.demo.user.domain.entity.User;

@Component
@RequiredArgsConstructor
public class BedMapper {

    private final UserMapper userMapper;
    private final ReservationRepository reservationRepository;

    public BedDto toDto(Bed bed) {
        UserDetailDto userDetailDto = null;

        // Bu yatağa ait şu an aktif olan rezervasyonu bul
        Optional<Reservation> activeReservationOpt = reservationRepository
                .findByBedIdAndIsActiveTrueAndIsCancelledFalse(bed.getId());

        if (activeReservationOpt.isPresent()) {
            Reservation activeReservation = activeReservationOpt.get();
            User user = activeReservation.getUser();
            userDetailDto = userMapper.toUserdatilDto(user);
        }

        return BedDto.builder()
                .id(bed.getId())
                .bedNumber(bed.getBedNumber())
                .isAvailable(bed.isAvailable())
                .userDetailDto(userDetailDto)
                .build();
    }
}

