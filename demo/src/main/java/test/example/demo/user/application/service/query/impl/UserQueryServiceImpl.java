package test.example.demo.user.application.service.query.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.DataResult;
import test.example.demo.core.utilities.results.ErrorDataResult;
import test.example.demo.core.utilities.results.SuccessDataResult;
import test.example.demo.user.application.service.query.UserQueryService;
import test.example.demo.user.dataaccess.repository.UserRepository;
import test.example.demo.user.domain.entity.User;
import test.example.demo.user.domain.enums.Role;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService{
	
	private final UserRepository userRepository;
	
	public DataResult<Optional<User>> getUserById(Long id){
		Optional<User> getUserId = userRepository.findById(id);
		if(getUserId.isPresent()){
			return new SuccessDataResult<Optional<User>>(getUserId, "Kullanıcı getrildi.");
		}
		
		return new ErrorDataResult<Optional<User>>(getUserId, "Kullanıcı bulunamadı");
	}

	@Override
	public DataResult<List<User>> getAllUsers() {
		
		List<User> user = userRepository.findAllUsers();
		
		return new SuccessDataResult<List<User>>(user, "Kullanıcıllar getirildi.");
	}

	@Override
	public DataResult<Optional<User>> getUserByTcNumber(String tcNumber) {

		Optional<User> getTc = userRepository.findByTcNumber(tcNumber);
		
		if(getTc.isPresent()) {
			return new SuccessDataResult<Optional<User>>(getTc, "Kullanıcı getrildi.");
		}
		
		return new ErrorDataResult<Optional<User>>(getTc, "Kullanıcı bulunamadı.");
	}

	@Override
	public DataResult<List<User>> getUsersByRole(Role role) {

		List<User> userRole = userRepository.findByRole(role);
		
		if(userRole.isEmpty()) {
			return new ErrorDataResult<List<User>>(userRole, "Bu tarzda bir kullanıcı bulunmuyor."); 
		}
		
		return new SuccessDataResult<List<User>>(userRole, "Kullanıcılar getirildi.");
	}

	@Override
	public DataResult<List<User>> searchUsersByNameOrSurname(String keyword) {

		List<User> getUser = userRepository.searchByNameOrSurname(keyword);
		
		if(getUser.isEmpty()) {
			return new ErrorDataResult<List<User>>(getUser, "Kullanıcı bulunamadı");
		}
		
		return new SuccessDataResult<List<User>>(getUser, "Kullanıcı veya kullanıcılar bulundu.");
	}

	@Override
	public DataResult<Optional<User>> getUserWithReservation(Long id) {

		Optional<User> userId = userRepository.findUserWithReservations(id);
	
		if(userId.isPresent()) {
			return new SuccessDataResult<Optional<User>>(userId, "Kullanıcı bulundu.");
		}
		
		return new ErrorDataResult<Optional<User>>(userId, "Kullanıcı bulunamadı.");
	}

}
