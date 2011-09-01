package tests5;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class ServiceBeforeAdvice implements MethodBeforeAdvice {

	@Resource
	private UserInfo userInfo;

	public void before(Method method, Object[] args, Object target) throws Throwable {
		Log log = LogFactory.getLog(getClass());
		log.info("user id:" + userInfo.getUserId() + " logined?:" + userInfo.isHasLogined());
		if (!userInfo.isHasLogined()) {
			throw new AuthException("Authentication error at " + method.getName() + " method.");
		}
	}
}
