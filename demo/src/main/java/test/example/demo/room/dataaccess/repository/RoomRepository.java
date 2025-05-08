package test.example.demo.room.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import test.example.demo.room.domain.entity.Room;
import test.example.demo.room.domain.enums.RoomType;

public interface RoomRepository extends JpaRepository<Room, Long>{
	
    @Query("SELECT r FROM Room r WHERE r.roomNumber = :roomNumber AND r.isActive = true")
    Optional<Room> findByRoomNumber(@Param("roomNumber") String roomNumber);

    @Query("SELECT r FROM Room r WHERE r.roomType = :roomType AND r.isActive = true")
    List<Room> findByRoomType(@Param("roomType") RoomType roomType);

    @Query("SELECT r FROM Room r WHERE r.isActive = true")
    List<Room> findAllActiveRooms();

    @Modifying
    @Query("UPDATE Room r SET r.isActive = :isActive WHERE r.id = :roomId")
    void updateRoomStatus(@Param("roomId") Long roomId, @Param("isActive") boolean isActive);

    @Query("SELECT r FROM Room r JOIN FETCH r.beds WHERE r.id = :roomId AND r.isActive = true")
    Optional<Room> findByIdWithBeds(@Param("roomId") Long roomId);
    
    @Query("SELECT COUNT(r) > 0 FROM Room r WHERE r.roomNumber = :roomNumber AND r.isActive = true")
    boolean existsByRoomNumber(@Param("roomNumber") String roomNumber);

}
