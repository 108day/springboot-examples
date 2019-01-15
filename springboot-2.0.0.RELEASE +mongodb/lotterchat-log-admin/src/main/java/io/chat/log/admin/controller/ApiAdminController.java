package io.chat.log.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.chat.log.admin.service.IUserRepositoryService;

/**
 * @author  108day
 */
@Controller
public class ApiAdminController {

	@Autowired
	private IUserRepositoryService userRepositoryService;
	
    @GetMapping(value = "/")
    public String hello(Map<String,Object> map) {
        String name = "jiangbei";
        map.put("name", name);
        return "index";
    }

    
    @GetMapping(value = "/save")
    public String save(Model model) {
    	userRepositoryService.findAll();
        return "list";
    }
    
    @GetMapping(value = "/list")
    public String list(Model model) {
    	model.addAttribute("list", userRepositoryService.findAll());
        return "list";
    }
   
}
