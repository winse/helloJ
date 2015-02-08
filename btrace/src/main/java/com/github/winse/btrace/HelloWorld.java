package com.github.winse.btrace;

// import all BTrace annotations
import java.io.IOException;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.*;
// import statics from BTraceUtils class
import static com.sun.btrace.BTraceUtils.*;

// @BTrace annotation tells that this is a BTrace program
@BTrace
public class HelloWorld {

	// @OnMethod annotation tells where to probe.
	// In this example, we are interested in entry
	// into the Thread.start() method.
	@OnMethod(clazz = "java.lang.Thread", method = "start")
	public static void traceThreadStart() {
		// println is defined in BTraceUtils
		// you can only call the static methods of BTraceUtils
		println("about to start a thread!");
	}
/*
	@OnMethod(clazz = "java.lang.String", method = "<init>")
	public static void traceConstruct1(@Self String self, byte[] values) throws IOException {
		// ERROR println(self + "" + Strings.str(values));
		println(Strings.concat(Strings.concat("traceConstruct1: ", self), Strings.concat("[", Strings.str(values))));
	}

	@OnMethod(clazz = "java.lang.String", method = "<init>")
	public static void traceConstruct0(@Self String self) {
		println(Strings.concat("traceConstruct0: ", self));
	}

	@OnMethod(clazz = "java.lang.StringBuilder", method = "toString")
	public static void traceToString(@Self StringBuilder self, @ProbeClassName String pcm, @ProbeMethodName String pmn) {
		println(Strings.concat("traceToString self: ", Strings.str(self)));
		println(Strings.strcat("traceToString Context: ", Strings.strcat(pcm, Strings.strcat("#", pmn))));
	}

	@OnMethod(clazz = "java.lang.StringBuilder", method = "toString", location = @Location(Kind.RETURN))
	public static void traceReturn(@Self StringBuilder self, @Return String result) {
//		println(Strings.concat("traceReturn self: ", Strings.str(self)));
//		println(Strings.strcat("traceReturn result: ", result));

		println(Strings.strcat("loaded ", result));
		Threads.jstack();
		println("==========================");
	}
*/
	
	// 查看访问内部调用那些实例的方法
	@OnMethod(clazz = "/.*HelloTest/", method = "testNewStringWithBytes", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
	public static void stringAppendCall(@Self AnyType self, @TargetInstance Object instance,
			@TargetMethodOrField String method) {
		println(Strings.concat("stringAppendCall self: ", Strings.str(self)));
		println(Strings.concat("stringAppendCall called-instace: ", Strings.str(instance)));

		print(method); 
		print(" ");
	}

}
