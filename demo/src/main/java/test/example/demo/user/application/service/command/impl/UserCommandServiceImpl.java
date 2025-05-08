package test.example.demo.user.application.service.command.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import test.example.demo.core.utilities.results.ErrorResult;
import test.example.demo.core.utilities.results.Result;
import test.example.demo.core.utilities.results.SuccessResult;
import test.example.demo.user.application.dto.UpdateUserRequest;
import test.example.demo.user.application.mapper.UserMapper;
import test.example.demo.user.application.service.command.UserCommandService;
import test.example.demo.user.dataaccess.repository.UserRepository;
import test.example.demo.user.domain.entity.User;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService{
	
	private final UserRepository userRepository;
	
	private final UserMapper userMapper;

	@Override
	public Result createUser(User user) {
		
		Optional<User> getTc = userRepository.findByTcNumber(user.getTcNumber());
		Optional<User> getEmail = userRepository.findByEmail(user.getEmail());
		
		if(getTc.isPresent() || getEmail.isPresent()) {
			return new ErrorResult("Bu kullanıcı zaten mevcut");
		}
		
		userRepository.save(user);
		return new SuccessResult("Kayıt başarılı");
	}

	@Override
	public Result updateUser(Long id, UpdateUserRequest request) {

		 Optional<User> user = userRepository.findById(id);
		
		if(user.isPresent()) {
			userMapper.updateUserFromDto(user.get(), request);
			userRepository.save(user.get()); 
			
			return new SuccessResult("Başarıyla güncellendi.");
		}
		
		return new ErrorResult("Kullanıcı bulunamadı");
	}

	@Override
	public Result deleteUser(Long id) {

		Optional<User> user = userRepository.findById(id);
		
		if(user.isPresent()) {
			
			userRepository.deleteById(id);
			
			return new SuccessResult("Kullanıcı başarıyla silindi.");
		}
		
		return new ErrorResult("Bu kullanıcı bulunamadı.");
		
	}

}
