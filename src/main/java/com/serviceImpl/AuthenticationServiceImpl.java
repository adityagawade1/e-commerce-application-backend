package com.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.security.auth.login.AccountLockedException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.controller.request.UserRequest;
import com.dto.UserDto;
import com.entity.User;
import com.exception.PasswordExpireException;
import com.exception.UnAuthorizedException;
import com.exception.UserNotFoundException;
import com.mysql.cj.exceptions.PasswordExpiredException;
import com.repository.UserRepository;
import com.security.JwtTokenUtil;
import com.service.AuthenticationService;
import com.service.UserService;
import com.utils.Constants;
import com.utils.ExpirationDate;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtTokenUtil jwtUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(noRollbackFor = { UnAuthorizedException.class, BadCredentialsException.class,
			PasswordExpiredException.class })
	public Map<String, Object> createJwtToken(String userName, String password)
			throws UnAuthorizedException, UserNotFoundException, AccountLockedException, PasswordExpireException {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName,
				password);

		User user = userService.getUserByEmailAndIsEnable(userName, true);

		if (ExpirationDate.checExpiredDate(user.getExpiredTime())) {
			
			throw new PasswordExpireException(Constants.PASSWORD_EXPIRED);
		}

		if (user.isAccountLock()) {
			throw new AccountLockedException("User Account locked need to set new credentials");
		}

		if (user.getLoginAttempt() >= 5) {
			user.setAccountLock(true);
			userService.updateUser(user);
			throw new UnAuthorizedException(Constants.REACHED_MAXIMUM_ATTEMPT);
		}
		Authentication authenticate;
		try {
			 authenticate = authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			user.setLoginAttempt(user.getLoginAttempt() + 1);
			userService.updateUser(user);
			throw new BadCredentialsException("Invalid credential");
		}

		SecurityContextHolder.getContext().setAuthentication(authenticate);

		String accessToken = jwtUtil.generateToken(authenticate);
		String refreshToken = jwtUtil.doRefreshToken(authenticate);

		Map<String, Object> response = new HashMap<>();
		response.put("accessToken", accessToken);
		response.put("refreshToken", refreshToken);
		response.put("user", modelMapper.map(user, UserDto.class));
		response.put("role", user.getRole().getRole());
		return response;
	}

	@Override
	public Map<String, String> refreshToken(String token) {

		String userName = JwtTokenUtil.getInstance().getUsernameFromToken(token);

		HashMap<String, Object> claims = new HashMap<>();
	//	String accessToken = jwtUtil.generateToken(claims, userName);
	//	String refreshToken = jwtUtil.doRefreshToken(claims, userName);

		Map<String, String> response = new HashMap<>();
//		response.put("accessToken", accessToken);
//		response.put("refreshToken", refreshToken);

		return response;
	}

	@Override
	@Transactional
	public void unLockUser(UserRequest userRequest) throws UserNotFoundException {

		User user = userService.getUserByEmailAndIsEnable(userRequest.getName(), true);

		user.setAccountLock(false);
		userService.updateUser(user);

	}

	@Override
	@Transactional
	public void updatePassword(UserRequest userRequest) throws UserNotFoundException {
		User user = userService.getUserByEmailAndIsEnable(userRequest.getName(), true);
		
		user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
		userService.updateUser(user);
	}

}
