package com.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Role;
import com.repository.RoleRepository;
import com.service.RoleService;
import com.utils.Constants;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;

	@Transactional
	public void saveRole() {
		
		Role role = new Role();
		role.setRole(Constants.ROLE_ADMIN);
		
		Role role1= new Role();
		role1.setRole(Constants.ROLE_USER);
		
		roleRepo.save(role);
		roleRepo.save(role1);
		
	}

	@Override
	public Role getRole(String name) {
		return roleRepo.findByRole(name);
	}

	@Override
	public List<Role> getRoles() {
		return roleRepo.findAll();
	}
	
	
	


}
