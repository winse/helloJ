import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author winse
 */
public class StaticFieldTest {

	@Test
	public void testClassLoad() {
		try {
			//加载类
			Class.forName("Model");
		} catch (ClassNotFoundException e) {
			fail();
		}
	}

	@Test
	public void testStaticFiled() {
		// 实例化
		Model firstInstance = new Model();
		firstInstance.staticArg = new Integer(3);
		assertTrue(Model.staticArg.equals(3));

		Model secondInstance = new Model();
		secondInstance.staticArg = "winse";
		assertTrue(Model.staticArg.equals("winse"));

		assertTrue(firstInstance.staticArg == secondInstance.staticArg);
	}

	@Test
	public void testLocalField() {
		Model first = new Model();
		first.localArg = new Integer(3);

		Model second = new Model();
		second.localArg = "winse";

		assertFalse(first.localArg == second.localArg);
	}
}

class Model {
	public static Object staticArg = get("Static Field");

	public Object localArg = get("Local field");

	private static Object get(String tip) {
		System.out.println(tip);
		return new Object();
	}
}