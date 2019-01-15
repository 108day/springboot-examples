package io.chat.log.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.chat.log.service.IAccessIPService;
import io.chat.common.vo.CustmerCriteria;
import io.chat.log.entity.AccessIpEntity;
import io.chat.log.vo.R;
/**
 * IP访问控制，管理接口提供给后面管理界面使用
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月7日
 */
@RestController
@RequestMapping("/api/accessip/")
public class ApiAccessIPController {

	@Autowired
	private IAccessIPService accessIPService;
	
	@RequestMapping("/list")
	public R list() {
		try {
			Map<String, CustmerCriteria> params =  new HashMap<>();
			accessIPService.findMany(params);
			return  R.ok();
		} catch (Exception e) {
			return R.error();
		}
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public R save(@RequestBody AccessIpEntity accessIpEntity) {
		try {
			accessIPService.save(accessIpEntity);
			return  R.ok();
		} catch (Exception e) {
			return R.error();
		}
	}
	
	@RequestMapping(value="/saveBatch",method=RequestMethod.POST)
	public R saveBatch(@RequestBody List<AccessIpEntity> list) {
		try {
			accessIPService.saveBatch(list);
			return  R.ok();
		} catch (Exception e) {
			return R.error();
		}
	}
	
	
}
