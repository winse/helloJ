package com.winse.helloworld;
/**
 * @author winse
 */
public class NHelloWorld {

	//
	static Object sarg = new Object() {
		{
			System.out.println("Hello World");
		}
	};

	//
	static {
		System.out.println("Hello World");
	}
	
	//
	Object arg = new Object(){
		{
			System.out.println("hello world");
		}
	};
	
	//
	{
		System.out.println("hello world");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new NHelloWorld();
	}

}
