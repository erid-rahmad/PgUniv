package com.mpc.pgateway.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TrxAspect {

	@Before("execution(* com.mpc.middleware.processor.CoreProcessor.processIstToCore(..))")
	public void trxAdvice(JoinPoint joinPoint){
	}
}
