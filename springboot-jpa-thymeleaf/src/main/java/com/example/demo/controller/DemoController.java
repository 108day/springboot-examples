package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import com.example.demo.entity.User;
import com.example.demo.services.IUserRepositoryService;
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

/**
 * 返回json格式的数据
 * @author  108day
 */
@RestController
public class DemoController {

    /*
    如果你为了方便也可以直接引用dao
     */
    @Autowired
    private UserRepository userRepository;
    /*
        新建service层，业务放到service进行处理
     */
    @Autowired
    private IUserRepositoryService userRepositoryService;

    @GetMapping(value = "/helloworld")
    public String hello(Model model) {
        String name = "jiangbei";
        model.addAttribute("name", name);
        return "helloWorld";
    }

    /**
     * JPA查询数据
     * @return
     */
    @RequestMapping("/demo")
    public Map getInfo(){
        HashMap map = new HashMap();
        map.put("user",userRepository.findByUserName("秦始皇"));
        return map;
    }
    
    /**
     * JPA查询数据,仔细观察会发现，我这个方法注解是getMapping ，可以省略映射路径，直接请求方法名
     * @return
     */
    @GetMapping
    public ModelAndView list(Model model) {
        model.addAttribute("userList",userRepositoryService.findAll());
        model.addAttribute("title", "用户管理");
        return new ModelAndView("list", "userModel", model);
    }

}
