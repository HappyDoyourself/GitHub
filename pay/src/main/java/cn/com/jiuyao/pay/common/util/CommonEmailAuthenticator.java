package cn.com.jiuyao.pay.common.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class CommonEmailAuthenticator extends Authenticator {
	String userName = null;
	String password = null;

	public CommonEmailAuthenticator() {
	}

	public CommonEmailAuthenticator(String username, String password) {
		this.userName = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}
}