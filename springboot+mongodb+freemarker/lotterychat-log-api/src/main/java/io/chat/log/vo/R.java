package io.chat.log.vo;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author
 * @email
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public R() {
		put("code", 0);
		put("msg", "success");
		put("timestamp", System.currentTimeMillis());
	}

	public static R error() {
		return error(500, "未知异常，请联系管理员");
	}

	public static R error(String msg) {
		return error(500, msg);
	}

	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		r.put("timestamp", System.currentTimeMillis());
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		r.put("timestamp", System.currentTimeMillis());
		return r;
	}

	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		r.put("timestamp", System.currentTimeMillis());
		return r;
	}

	public static R ok() {
		return new R();
	}

	public static R okwithdata(Object data) {
		R r = new R();
		r.put("data", data);
		r.put("timestamp", System.currentTimeMillis());
		return r;
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}