package com.example.demo.controller;

import com.example.demo.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 集成页面用 restController VS Controller
 * @author  108day
 */
@Controller
public class WebController {

    @GetMapping(value = "/hello")
    public String hello(Model model) {
        String name = "jiangbei";
        model.addAttribute("name", name);
        return "hello";
    }

   
}
