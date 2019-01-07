package io.chat.log.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
/**
 * ip 注册列表
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
@Data
public class AccessIpEntity implements Serializable{
	
	/*
	 * @author Kevin zhaosheji.kevin@gmail.com
	 * @date 2019年1月5日
	 */
	private static final long serialVersionUID = -3468297161679393934L;

	private String ip;
	
	private String title;
	
	private String scope;
	
	private Date createdTime;
	
	private String createdBy;
	
	private Date LastModifyTime;
	
	private String LastModifyBy;
	

}
