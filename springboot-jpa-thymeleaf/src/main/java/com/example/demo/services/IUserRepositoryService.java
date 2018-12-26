package com.example.demo.services;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;

import java.util.List;

public interface IUserRepositoryService{

    public List<User> findAll();

}
