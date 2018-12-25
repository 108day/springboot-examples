package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/demo")
    public Map getInfo(){
        HashMap map = new HashMap();
        map.put("hello","demo");
        User user =   userRepository.findByUserName("wangjl");
        List<User> list = userRepository.findAll();
        map.put("user",user);
        map.put("list",list);
        return map;
    }
    
    /**
     * 查询所用用户
     * @return
     */
    @GetMapping
    public ModelAndView list(Model model) {
        model.addAttribute("userList",userRepository.findAll() );
        model.addAttribute("title", "用户管理");
        return new ModelAndView("list", "userModel", model);
    }

}
