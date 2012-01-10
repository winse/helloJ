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
		Customer customer = site.getCustomer();

		String customerName;
		if (customer == null) {
			customerName = "occupant";
		} else {
			customerName = customer.getName();
			fail();
		}
	}

	@Test
	public void testNullHistory() {
		Customer customer = site.getCustomer();

		int weeksDelinquent;
		if (customer == null) {
			weeksDelinquent = 0;
		} else {
			weeksDelinquent = customer.getPaymentHistory().getWeeksDelinquentInLastYear();
			fail();
		}
	}

}

class Site {
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}
}

class Customer {
	private String name = "Unspecified";
	private PaymentHistory paymentHistory;

	public String getName() {
		return name;
	}

	public PaymentHistory getPaymentHistory() {
		return paymentHistory;
	}
}

class PaymentHistory {

	public int getWeeksDelinquentInLastYear() {
		throw new UnsupportedOperationException();
	}
}