package tests5;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.aop.AfterReturningAdvice;

public class LoginServiceAfterAdvice implements AfterReturningAdvice {
	
	@Resource
	private UserInfo userInfo;

	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		// ���O�C���������AuserInfo�ɒl���Z�b�g����B
		if ((Boolean) returnValue) {
			userInfo.setUserId((String) args[0]/*userId*/);
			userInfo.setHasLogined(true);
		}
	}
}