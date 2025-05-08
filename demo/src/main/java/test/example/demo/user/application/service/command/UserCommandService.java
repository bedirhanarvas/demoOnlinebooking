package test.example.demo.user.application.service.command;

import test.example.demo.core.utilities.results.Result;
import test.example.demo.user.application.dto.UpdateUserRequest;
import test.example.demo.user.domain.entity.User;

public interface UserCommandService {

	Result createUser(User user);
	
	Result updateUser(Long id, UpdateUserRequest request);
	
	Result deleteUser(Long id);
}
