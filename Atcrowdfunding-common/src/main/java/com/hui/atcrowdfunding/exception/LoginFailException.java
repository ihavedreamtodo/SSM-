package com.hui.atcrowdfunding.exception;


public class LoginFailException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//生成的序列号表示这个类是否支持序列化

	public LoginFailException(String message) {
		super(message);//调用父类的构造方法就行
	}
}
