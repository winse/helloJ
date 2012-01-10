import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 系统在向对象发送一个消息之前，总要检查对象是否存在。 <br/>
 * 我们可能会向一个对象索求它所相关的Person对象，然后再问那个对象是否为null。<br/>
 * 如果对象的确存在，我们才能调用它的rate()函数以及查询这个人的薪资级别。<br/>
 * 我们在好些地方都要这样做，造成的重复代码让我们很烦心。
 * 
 * @author winse
 */
public class NullObjectTest {

	private Site site;

	@Before
	public void init() {
		site = new Site();
	}

	@Test
	public void testNullName() {
		ICustomer customer = site.getCustomer();
		String customerName = customer.getName();

		assertTrue(customerName != null);
	}

}

class Site {
	private ICustomer customer;

	public ICustomer getCustomer() {
		if (customer != null) {
			return customer;
		}
		return NullCustomer.getInstance();
	}

	public void setCustomer(ICustomer customer) {
		this.customer = customer;
	}
}

interface ICustomer {
	String getName();
}

class Customer implements ICustomer {
	private String name;

	public String getName() {
		return name;
	}
}

final class NullCustomer implements ICustomer {
	private final static ICustomer INSTACNE = new NullCustomer();

	public static ICustomer getInstance() {
		return INSTACNE;
	}

	private static final String NAME = "occupant";

	@Override
	public String getName() {
		return NAME;
	}

}
