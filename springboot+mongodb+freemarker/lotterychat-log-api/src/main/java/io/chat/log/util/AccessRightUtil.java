package io.chat.log.util;

import java.util.List;

import io.chat.log.entity.AccessIpEntity;
import io.chat.log.service.AccessIPServiceImpl;

public class AccessRightUtil {

	
	private static List<AccessIpEntity> accessList;
	
	static {
		try {
			accessList = new AccessIPServiceImpl().list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<AccessIpEntity> getAccessRight(){
		try {
			if(accessList!=null) {
				return accessList;
			}else {
				return accessList = new AccessIPServiceImpl().list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessList;
	}
}
