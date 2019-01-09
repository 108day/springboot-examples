package io.chat.log.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chat.log.dao.IOnlineUserDao;
import io.chat.log.vo.CustmerCriteria;

@Service("onlineUserService")
public class OnlineUserServiceImpl implements IOnlineUserService {
	
	@Autowired
	private IOnlineUserDao onlineUserDao;

	private static final String USER_ONLINE_COLLECTION= "onlineUser";
	
	@Override
	public void saveOnline(Map<String, Object> user) throws Exception {
		onlineUserDao.insertOne(USER_ONLINE_COLLECTION, user);
	}

	@Override
	public Object findOnline(Map<String, CustmerCriteria> user) throws Exception {
		return onlineUserDao.findMany(USER_ONLINE_COLLECTION, user);
	}

	@Override
	public long deleteOnline(String userId) throws Exception {
		Map<String, CustmerCriteria> user  = new HashMap<>();
		user.put("userId", new CustmerCriteria("=",userId));
		return onlineUserDao.delete(USER_ONLINE_COLLECTION, user);
	}
	
}
