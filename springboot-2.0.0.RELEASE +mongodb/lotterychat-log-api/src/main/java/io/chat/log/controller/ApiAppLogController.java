package io.chat.log.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.util.JSON;

import io.chat.common.service.IAppLogService;
import io.chat.log.vo.R;
/**
 * 记录日志
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月7日
 */
@RestController
@RequestMapping("/api/log")
public class ApiAppLogController {

	@Autowired
	protected IAppLogService appLogService;
	
	protected final String collectionName ="mongodb_log"; //创建日志集合名称为 mongodb_log
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public R save(@RequestBody Map<String,Object> map) {
		try {
			appLogService.save(collectionName, map);
			return  R.ok();
		} catch (Exception e) {
			return  R.error();
		}
	}
}
