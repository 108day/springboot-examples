package io.chat.log.util;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

import io.chat.log.vo.CustmerCriteria;

public class MongoQueryUtil {


	/**
	 * 根据Map构建查询语句
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author sheji zhaosheji.kevin@gmail.com
	 * @date 2019年1月4日
	 */
	public static Query buildQuery(Query query,Map<String,CustmerCriteria> params) {
		Assert.notNull(query,"The parameter 'query'  must not be null !");
		//Assert.notNull(params,"The parameter 'params'  must not be null !");
		if(params != null ) {
			params.forEach((key,custmerCriteria)->{
				String express = custmerCriteria.getExpress();
				 Object value = custmerCriteria.getValue();
				 Assert.notNull(key,"The parameter key of params must not be null !");
				 Assert.notNull(express,"The parameter CustmerCriteria.express of "+key+" must not be null !");
				 Assert.notNull(value,"The parameter CustmerCriteria.value of "+key+" must not be null !");
				 if("=".equals(express)) {
					 query.addCriteria(Criteria.where(key).is(value));//查询条件
				 }else if(">=".equals(express)) {
					 query.addCriteria(Criteria.where(key).gte(value));
				 }else if("<=".equals(express)) {
					 query.addCriteria(Criteria.where(key).lte(value));
				 }else if(">".equals(express)) {
					 query.addCriteria(Criteria.where(key).gt(value));//查询条件
				 }else if("<".equals(express)) {
					 query.addCriteria(Criteria.where(key).lt(value));//查询条件
				 }else if("like".equals(express)) {
					 query.addCriteria(Criteria.where(key).regex(custmerCriteria.getValue().toString()));
				 }else if("between".equals(express)) {
					 if(value instanceof Long []) {
						 Long [] list= (Long[])value ;
						 if(list.length != 2) {
							 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" length must be 2 !");
						 }
						 if(list[0] > list[1]) {
							 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" first  must be bigger than second !");
						 }
						 query.addCriteria(Criteria.where(key).gte(list[0]).lte(list[1]));
					 }else {
						 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" format must be array !");
					 }
				 }else if("in".equals(express)) {
					 if(value instanceof String [] || value instanceof List ) {
						 query.addCriteria(Criteria.where(key).in(value));//查询条件
					 }else {
						 throw new IllegalArgumentException("The parameter key="+key+" of value="+value+" format must be array !");
					 }
				 }
				 if(value instanceof Direction) {
					 query.with(Sort.by((Direction)value,key)); //排序
				 }else {
					 query.with(Sort.by(Direction.ASC,"_id")); //排序
				 }
			 });
		}
		return query;
	}
}
