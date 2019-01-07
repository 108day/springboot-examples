package io.chat.log.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class HistoryChatMessageEnity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2937277126392004535L;
	
	private String roomId;
	
	private Long createdTime;
	
	private Object jsonObject;
	
	public HistoryChatMessageEnity(String roomId, Object jsonObject, Long createdTime) {
		super();
		this.roomId = roomId;
		this.jsonObject = jsonObject;
		this.createdTime = createdTime;
	}
	public HistoryChatMessageEnity() {
		super();
	}
	
}
