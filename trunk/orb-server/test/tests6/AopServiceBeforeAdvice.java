package tests6;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.MethodBeforeAdvice;

public class AopServiceBeforeAdvice implements MethodBeforeAdvice
{
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable 
	{
		Log log = LogFactory.getLog(getClass());
		log.info("before ...." + arg0.getName());
	}
}
