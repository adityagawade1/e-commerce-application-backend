package com.serviceImpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.controller.request.UserRequest;
import com.dto.UserDto;
import com.entity.User;
import com.exception.UserNotFoundException;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import com.service.UserService;
import com.utils.Constants;
import com.utils.ExpirationDate;
import com.utils.LOG;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void createUser(UserRequest user) {

		User newUser = new User();
		newUser.setName(user.getName());
		newUser.setEmail(user.getEmail());
		newUser.setGender(user.getGender());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setUserName(user.getEmail());
		newUser.setExpiredTime(String.valueOf(ExpirationDate.getExpirationDate()));
		newUser.setEnable(true);
		newUser.setLoginAttempt(0);
		newUser.setAccountLock(false);

		if (user.getEmail().contains("fortytwo42.in")) {
			newUser.setRole(roleRepo.findByRole(Constants.ROLE_ADMIN));
		} else {
			newUser.setRole(roleRepo.findByRole(Constants.ROLE_USER));
		}

		userRepo.save(newUser);

	}

	@Override
	public User getUserByEmailAndIsEnable(String userName, boolean b) throws UserNotFoundException {

		return userRepo.findByEmailAndIsEnable(userName, true)
				.orElseThrow(() -> new UserNotFoundException(String.format(Constants.USER_NOT_FOUND, userName)));
	}

	@Override
	public User getUser(String mail) {

		Optional<User> user = userRepo.findByEmail(mail);
		return user.isPresent() ? user.get() : null;
	}

	@Override
	public void updateUser(User user) throws UserNotFoundException {
		User getUser = userRepo.findByIdAndIsEnable(user.getId(), true)
				.orElseThrow(() -> new UserNotFoundException(String.format(Constants.USER_NOT_FOUND, user.getName())));

		getUser.setName(user.getName());
		getUser.setEmail(user.getEmail());
		getUser.setAccountLock(user.isAccountLock());
		getUser.setEnable(user.isEnable());
		getUser.setExpiredTime(user.getExpiredTime());
		getUser.setGender(user.getGender());
		getUser.setLoginAttempt(user.getLoginAttempt());
		getUser.setPassword(user.getPassword());
		getUser.setRole(user.getRole());
		getUser.setUserName(user.getUsername());
		getUser.setAccountLock(user.isAccountLock());

		userRepo.save(getUser);
	}

	@Override
	public UserDto getUser(int id) {
		
		LOG.info("In get user for id  "+ id);
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			LOG.info("User :"+user.get());
			return modelMapper.map(user.get(), UserDto.class);
		}
		
		return null;
	}

}
