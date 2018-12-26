package com.example.demo.services;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

import java.util.List;

public interface IUserRepositoryService{

    /**
     * 服务一般都写在service层
     * findAll方法名随便起的，和JPA没有关系
     */
    public List<User> findAll();

}
