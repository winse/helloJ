package com.github.winse.springaop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.github.winse.springaop.service.HelloWorld;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAutoConfiguration
@ComponentScan
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		Application app = ctx.getBean(Application.class);
		app.execute();
	}

	@Autowired
	private HelloWorld runner;

	public void execute() {
		runner.echo();
	}
}
