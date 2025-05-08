package test.example.demo.reservation.application.service.command.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import test.example.demo.bed.dataaccess.repository.BedRepository;
import test.example.demo.bed.domain.entity.Bed;
import test.example.demo.core.utilities.results.ErrorResult;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.core.utilities.results.SuccessResult;
import test.example.demo.reservation.application.dto.ReservationCreateDto;
import test.example.demo.reservation.application.dto.ReservationUpdateDto;
import test.example.demo.reservation.application.mapper.ReservationMapper;
import test.example.demo.reservation.application.service.command.ReservationCommandService;
import test.example.demo.reservation.dataaccess.repository.ReservationRepository;
import test.example.demo.reservation.domain.entity.Reservation;
import test.example.demo.user.dataaccess.repository.UserRepository;
import test.example.demo.user.domain.entity.User;

@Service
@RequiredArgsConstructor
public class ReservationCommandServiceImpl implements ReservationCommandService {

	private final ReservationRepository reservationRepository;
	private final UserRepository userRepository;
	private final BedRepository bedRepository;
	private final ReservationMapper reservationMapper;

	@Override
	public Result createReservation(ReservationCreateDto createDto) {

		User user = userRepository.findById(createDto.getUserId())
				.orElseThrow(() -> new IllegalArgumentException("Kullanıcı bulunamadı"));

		Bed bed = bedRepository.findById(createDto.getBedId())
				.orElseThrow(() -> new IllegalArgumentException("Yatak bulunamadı"));

		if (createDto.getStartingDate().isAfter(createDto.getFinishingDate())) {
			return new ErrorResult("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
		}

		if (reservationRepository.existsByBedIdAndStartingDateBeforeAndFinishingDateAfter(createDto.getBedId(),
				createDto.getFinishingDate(), createDto.getStartingDate())) {
			return new ErrorResult("Bu yatak, seçilen tarihlerde zaten rezerve edilmiş.");
		}

		Reservation reservation = reservationMapper.toEntity(createDto, user, bed);
		reservationRepository.save(reservation);
		bed.setAvailable(false);
		bedRepository.save(bed);

		return new SuccessResult("Rezervasyon başarılı bir şekilde oluşturuldu.");
	}

	@Override
	public Result cancelReservation(Long reservationId) {
		Reservation reservation = reservationRepository.findById(reservationId)
				.orElseThrow(() -> new IllegalArgumentException("Rezervasyon bulunamadı"));

		reservation.setCancelled(true);
		reservation.setActive(false);

		reservationRepository.save(reservation);

		return new SuccessResult("Rezervasyon başarıyla iptal edildi.");

	}

	@Override
	public Result updateReservation(Long reservationId, ReservationUpdateDto dto) {

		Optional<User> userOptional = userRepository.findById(dto.getUserId());
		if (userOptional.isEmpty()) {
			return new ErrorResult("Kullanıcı bulunamadı.");
		}

		Optional<Bed> bedOptional = bedRepository.findById(dto.getNewBedId());
		if (bedOptional.isEmpty()) {
			return new ErrorResult("Yatak bulunamadı.");
		}

		Optional<Reservation> existingReservationOptional = reservationRepository.findById(reservationId);
		if (existingReservationOptional.isEmpty()) {
			return new ErrorResult("Rezervasyon bulunamadı.");
		}

		Reservation existingReservation = existingReservationOptional.get();

		boolean conflictExists = reservationRepository.existsByBedIdAndStartingDateBeforeAndFinishingDateAfterAndIdNot(
				dto.getNewBedId(), reservationId, dto.getNewStartDate(), dto.getNewEndDate());

		if (conflictExists) {
			return new ErrorResult("Seçtiğiniz tarihlerde bu yatak için başka bir rezervasyon bulunuyor.");
		}

		existingReservation.setStartingDate(dto.getNewStartDate());
		existingReservation.setFinishingDate(dto.getNewEndDate());
		existingReservation.setBed(bedOptional.get());
		existingReservation.setUser(userOptional.get());

		reservationRepository.save(existingReservation);

		return new SuccessResult("Rezervasyon başarıyla güncellendi.");

	}

	@Override
	@Transactional
	public Result deleteReservation(Long reservationId) {
		Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
		if (reservationOpt.isEmpty()) {
			return new ErrorResult("Rezervasyon bulunamadı.");
		}

		Reservation reservation = reservationOpt.get();

		if (!reservation.isActive()) {
			return new ErrorResult("Pasif rezervasyon silinemez.");
		}

		reservationRepository.delete(reservation);

		reservation.getBed().setAvailable(true);

		return new SuccessResult("Rezervasyon başarıyla silindi.");
	}

}
