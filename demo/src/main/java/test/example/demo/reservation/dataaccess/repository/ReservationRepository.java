package test.example.demo.reservation.dataaccess.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.example.demo.reservation.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r WHERE r.finishingDate >= CURRENT_DATE AND r.isCancelled = false")
	List<Reservation> findAllActiveReservations();

	@Query("SELECT r FROM Reservation r WHERE r.user.id = :userId")
	List<Reservation> findAllByUserId(@Param("userId") Long userId);

	@Query("SELECT r FROM Reservation r WHERE r.user.id = :userId AND r.isCancelled = false AND r.isActive = true")
	List<Reservation> findActiveReservationsByUserId(@Param("userId") Long userId);

	@Query("SELECT r FROM Reservation r WHERE r.startingDate <= :date AND r.finishingDate >= :date")
	List<Reservation> findReservationsOnDate(@Param("date") LocalDateTime date);

	@Query("SELECT r FROM Reservation r WHERE r.id = :reservationId AND r.user.id = :userId")
	Optional<Reservation> findByIdAndUserId(@Param("reservationId") Long reservationId, @Param("userId") Long userId);

	@Query("""
			    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
			    FROM Reservation r
			    WHERE r.user.id = :userId AND r.startingDate < :end AND r.finishingDate > :start
			""")
	boolean existsByUserIdAndStartingDateBeforeAndFinishingDateAfter(@Param("userId") Long userId,
			@Param("end") LocalDateTime end, @Param("start") LocalDateTime start);

	@Query("""
			    SELECT r FROM Reservation r
			    WHERE r.bed.id = :bedId AND r.id != :excludeId AND r.startingDate < :end AND r.finishingDate > :start
			""")
	List<Reservation> findConflictingReservationsExcept(@Param("bedId") Long bedId, @Param("excludeId") Long excludeId,
			@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	@Query("""
			    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
			    FROM Reservation r
			    WHERE r.bed.id = :bedId AND r.startingDate < :end AND r.finishingDate > :start
			""")
	boolean existsByBedIdAndStartingDateBeforeAndFinishingDateAfter(@Param("bedId") Long bedId,
			@Param("end") LocalDateTime end, @Param("start") LocalDateTime start);

	@Query("""
			    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
			    FROM Reservation r
			    WHERE r.bed.id = :bedId AND r.id != :excludeId AND r.startingDate < :end AND r.finishingDate > :start
			""")
	boolean existsByBedIdAndStartingDateBeforeAndFinishingDateAfterAndIdNot(@Param("bedId") Long bedId,
			@Param("excludeId") Long excludeId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	@Query("SELECT r FROM Reservation r WHERE r.bed.id = :bedId AND r.isActive = true AND r.isCancelled = false")
	Optional<Reservation> findByBedIdAndIsActiveTrueAndIsCancelledFalse(@Param("bedId") Long bedId);
	
	@Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Reservation r WHERE r.bed.id = :bedId")
	boolean existsByBedId(@Param("bedId") Long bedId);



}
