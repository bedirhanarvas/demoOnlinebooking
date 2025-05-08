package test.example.demo.reservation.application.service.query.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.core.utilities.results.ErrorDataResult;
import test.example.demo.core.utilities.results.SuccessDataResult;
import test.example.demo.reservation.application.dto.ActiveReservationDto;
import test.example.demo.reservation.application.mapper.ReservationMapper;
import test.example.demo.reservation.application.service.query.ReservationQueryService;
import test.example.demo.reservation.dataaccess.repository.ReservationRepository;
import test.example.demo.reservation.domain.entity.Reservation;

@Service
@RequiredArgsConstructor
public class ReservationQueryServiceImpl implements ReservationQueryService {

	private final ReservationRepository reservationRepository;
	private final ReservationMapper reservationMapper;

	@Override
	public DataResult<ActiveReservationDto> getActiveReservationById(Long reservationId) {
		Optional<Reservation> optionalReservation = reservationRepository.findById(reservationId);

	    if (optionalReservation.isEmpty()) {
	        return new ErrorDataResult<ActiveReservationDto>(null,"Rezervasyon bulunamadı.");
	    }

	    Reservation reservation = optionalReservation.get();

	    if (!reservation.isActive() || reservation.isCancelled()) {
	        return new ErrorDataResult<ActiveReservationDto>(null,"Aktif rezervasyon bulunamadı.");
	    }

	    ActiveReservationDto dto = reservationMapper.toActiveDto(reservation);
	    return new SuccessDataResult<>(dto, "Aktif rezervasyon getirildi.");
        		
	}

	@Override
	public DataResult<List<ActiveReservationDto>> getActiveReservationsByUserId(Long userId) {
		List<Reservation> reservations = reservationRepository.findActiveReservationsByUserId(userId);
        List<ActiveReservationDto> dtoList = reservations.stream()
                .map(reservationMapper::toActiveDto)
                .toList();
        return new SuccessDataResult<>(dtoList, "Kullanıcının aktif rezervasyonları listelendi.");
	}

	@Override
	public DataResult<List<ActiveReservationDto>> getActiveReservationsByBedId(Long bedId) {
		List<Reservation> reservations = reservationRepository.findAll().stream()
                .filter(res -> res.getBed().getId().equals(bedId) && res.isActive() && !res.isCancelled())
                .toList();

        List<ActiveReservationDto> dtoList = reservations.stream()
                .map(reservationMapper::toActiveDto)
                .toList();

        return new SuccessDataResult<>(dtoList, "Yatağa ait aktif rezervasyonlar listelendi.");
	}

	@Override
	public DataResult<List<ActiveReservationDto>> getAllActiveReservations() {
		List<Reservation> reservations = reservationRepository.findAllActiveReservations();
        List<ActiveReservationDto> dtoList = reservations.stream()
                .map(reservationMapper::toActiveDto)
                .toList();

        return new SuccessDataResult<>(dtoList, "Tüm aktif rezervasyonlar listelendi.");
	}

	@Override
	public DataResult<List<ActiveReservationDto>> getReservationsOnDate(LocalDateTime date) {
		List<Reservation> reservations = reservationRepository.findReservationsOnDate(date);
        List<ActiveReservationDto> dtoList = reservations.stream()
                .filter(r -> !r.isCancelled() && r.isActive())
                .map(reservationMapper::toActiveDto)
                .toList();

        return new SuccessDataResult<>(dtoList, date + " tarihindeki aktif rezervasyonlar listelendi.");
	}

}
