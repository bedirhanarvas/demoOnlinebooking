package test.example.demo.user.application.service.query;

import java.util.List;
import java.util.Optional;

import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.user.domain.entity.User;
import test.example.demo.user.domain.enums.Role;

public interface UserQueryService {
	
	DataResult<Optional<User>> getUserById(Long id);

	DataResult<List<User>> getAllUsers();
	
	DataResult<Optional<User>> getUserByTcNumber(String tcNumber);
		
	DataResult<List<User>> getUsersByRole(Role role);
	
	DataResult<List<User>> searchUsersByNameOrSurname(String keyword);
	
	DataResult<Optional<User>> getUserWithReservation(Long id);
}
