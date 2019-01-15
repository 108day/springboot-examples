package io.chat.log.admin.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


import io.chat.common.mongo.MongoCommonDaoImpl;
import io.chat.common.vo.CustmerCriteria;
import io.chat.log.admin.entity.SysUser;

@Service("userRepository")
public class UserRepository extends  MongoCommonDaoImpl {

	public Object findAll() {
		Map<String,CustmerCriteria> params =new HashMap<>();
		Query query = this.buildQuery(new Query(), params);
		return this.mongoTemplate.find(query, SysUser.class);
	}

}
