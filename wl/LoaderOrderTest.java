/**
 * @author winse
 */
public class LoaderOrderTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
//		Class.forName("Child");
		 new Child("");
	}

}

class Util {

	public static Object get(String obj) {
		System.out.println(obj);

		return new Object();
	}
}

class Parent {

	static {
		Util.get("parent static blank1");
	}

	static Object sarg1 = Util.get("parent static arg1");
	static Object sarg2 = Util.get("parent static arg2");

	{
		Util.get("parent blank1");
	}

	Object arg = Util.get("parent arg");

	{
		Util.get("parent blank2");
	}

	public Parent() {
		Util.get("parent construct");
	}
	
	public Parent(String name) {
		Util.get("parent construct with argument");
	}

}

class Child extends Parent {

	static Object sarg = Util.get("child static arg");

	Object arg = Util.get("child arg");

	public Child() {
		Util.get("child construct");
	}
	
	public Child(String name) {
		super(name);
		Util.get("child construct with arg");
	}

}