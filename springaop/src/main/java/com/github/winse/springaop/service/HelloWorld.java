package com.github.winse.springaop.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld {

	private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

	public void echo() {
		LOG.info("hello world.");
	}

}
