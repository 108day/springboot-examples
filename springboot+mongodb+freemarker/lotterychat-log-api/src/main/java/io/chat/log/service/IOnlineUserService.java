package io.chat.log.service;

import java.util.Map;

import io.chat.log.vo.CustmerCriteria;
/**
 * 在线用户操作
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月9日
 */
public interface IOnlineUserService {
	/**
	 * 保存上线用户
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author Kevin zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public void saveOnline(Map<String,Object> user)throws Exception ;
	/**
	 * 查找上线用户
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author Kevin zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public Object findOnline(Map<String,CustmerCriteria> user)throws Exception ;
	
	/**
	 * 删除下线用户
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author Kevin zhaosheji.kevin@gmail.com
	 * @date 2019年1月9日
	 */
	public long deleteOnline(String userId)throws Exception ;
	
}
