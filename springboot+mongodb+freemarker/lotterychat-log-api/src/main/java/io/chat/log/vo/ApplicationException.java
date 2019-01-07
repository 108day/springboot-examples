package io.chat.log.vo;

/**
 * 自定义异常
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
    private String msg;
    private int code = 500;
    
    public ApplicationException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public ApplicationException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public ApplicationException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public ApplicationException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}