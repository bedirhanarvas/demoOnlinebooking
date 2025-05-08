package test.example.demo.user.dataaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import test.example.demo.user.domain.entity.User;
import test.example.demo.user.domain.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	

    @Query("SELECT u FROM User u")
    List<User> findAllUsers();
	
	@Query("SELECT u FROM User u WHERE u.tc_number = :tc")
	Optional<User> findByTcNumber(String tc);
	
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.name = :name AND u.surname = :surname")
	List<User> findByNameAndSurName(String name, String surname);

	@Query("SELECT u FROM User u WHERE u.role = :role")
	List<User> findByRole(Role role);
	
	@Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	List<User> searchByNameOrSurname(@Param("keyword") String keyword);
	
	@Query("SELECT u FROM User u LEFT JOIN FETCH u.reservations WHERE u.id = :userId")
	Optional<User> findUserWithReservations(@Param("userId") Long userId);

}
