package io.chat.log.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class SendMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * msgId用于删除
	 */
	private String msgid;
	/**
	 * 消息大类
	 */
	private String type;
	/**
	 * 消息子类
	 */
	private String mtype;
	/**
	 * 发送者id
	 */
	private String fromuserid;
	/**
	 * 发送者昵称
	 */
	private String fromnickname;
	/**
	 * 发送者头像
	 */
	private String fromuserheadimage;
	/**
	 * 发送者用户类型
	 */
	private Integer fromuseruserflag;
	/**
	 * 发送者等级
	 */
	private Integer fromuseruserdegree;
	/**
	 * 接收者id
	 */
	private String touserid;
	/**
	 * 接收者昵称
	 */
	private String tousernickname;
	/**
	 * 接收者头像
	 */
	private String touserheadimage;
	/**
	 * 发送内容
	 */
	private JSONObject content;
	/**
	 * 消息发送时间
	 */
//	private Date msgtime = new Date();
	private long msgtime;

}