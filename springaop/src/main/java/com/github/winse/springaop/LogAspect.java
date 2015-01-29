package com.github.winse.springaop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private static final Logger LOG = LoggerFactory.getLogger("c.g.w.s.LogAspect");

	@Before("execution(* com.github.winse.springaop.service.*.*(..))")
	public void start(JoinPoint jp) {
		LOG.debug("{}", jp);
	}

}
