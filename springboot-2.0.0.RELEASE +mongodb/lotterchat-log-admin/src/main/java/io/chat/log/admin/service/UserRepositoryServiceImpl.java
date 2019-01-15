package io.chat.log.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chat.log.admin.dao.UserRepository;
import io.chat.log.admin.entity.SysUser;

import java.util.List;

@Service("userRepositoryService")
public class UserRepositoryServiceImpl implements IUserRepositoryService {

    @Autowired
    private UserRepository userRepository;


	@Override
	public List<SysUser> findAll() {
		// TODO Auto-generated method stub
		return (List<SysUser>)userRepository.findAll();
	}
}
