package tests5;

import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class ServiceThrowsAdvice implements ThrowsAdvice {

	public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
		System.out.println("throws....");
	}
}