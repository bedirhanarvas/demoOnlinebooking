package test.example.demo.reservation.application.mapper;

import org.springframework.stereotype.Component;

import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.reservation.application.dto.ActiveReservationDto;
import test.example.demo.reservation.application.dto.ReservationCreateDto;
import test.example.demo.reservation.domain.entity.Reservation;
import test.example.demo.user.domain.entity.User;

@Component
public class ReservationMapper {

    public Reservation toEntity(ReservationCreateDto dto, User user, Bed bed) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setBed(bed);
        reservation.setStartingDate(dto.getStartingDate());
        reservation.setFinishingDate(dto.getFinishingDate());
        reservation.setCancelled(false);
        reservation.setActive(true);
        return reservation;
    }

    public ActiveReservationDto toActiveDto(Reservation reservation) {
        ActiveReservationDto dto = new ActiveReservationDto();
        dto.setReservationNumber(reservation.getReservationNumber());
        dto.setStartingDate(reservation.getStartingDate());
        dto.setFinishingDate(reservation.getFinishingDate());
        dto.setBedId(reservation.getBed().getId());
        return dto;
    }
}
