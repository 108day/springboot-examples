package io.chat.common.vo;

import java.io.Serializable;

import lombok.Data;
/**
 * MongoDB操作符转换
 * @author sheji zhaosheji.kevin@gmail.com
 * @date 2019年1月4日
 * @see CustmerCriteria.express 表达式 ，等同于 数据库操作符
 * @see CustmerCriteria.value 对象是 表达式右边的值
 * @see 类似：select * from t.className where Map.key CustmerCriteria.express CustmerCriteria.value
 * @see Map.key 对象 t存在的 字段名 String类型
 * @see CustmerCriteria.express:{可取值：>=,>,<=,<,=,like,in} 
 * @see CustmerCriteria.value 当 表达式 是 like 时 值为 String 类型 ，当 表达式 为 in 时 ，值为 集合类型
 */
@Data
public class CustmerCriteria implements Serializable {
	
	/*
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	private static final long serialVersionUID = 2561649054641831496L;

	private String express;
	
	private Object value ;

	public CustmerCriteria(String express, Object value) {
		super();
		this.express = express;
		this.value = value;
	}

	public CustmerCriteria() {
		super();
	}
	
	
	
	

}
