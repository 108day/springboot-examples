package io.chat.log.admin.service;


import java.util.List;

import io.chat.log.admin.entity.SysUser;

public interface IUserRepositoryService{

	
	
    /**
     * 服务一般都写在service层
     * findAll方法名随便起的，和JPA没有关系
     */
    public List<SysUser> findAll();

}
