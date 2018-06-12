package be.vdab.toysforboys.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.entities.Product;

public class OrderDetailTest {
	private Product product;
	private Orderdetail orderdetail1, orderdetail2;
	
	@Before
	public void before(){
		product = new Product("testName1");
		product.addInStock(20);
		product.addInOrder(35);
		orderdetail1 = new Orderdetail(product,5,BigDecimal.TEN);
		orderdetail2 = new Orderdetail(product,30,BigDecimal.TEN);
	}
	
	@Test
	public void getValue() {
		assertEquals(BigDecimal.valueOf(50),orderdetail1.getValue());
	}
	
	@Test
	public void isDeliverable_returns_true_when_deliverable() {
		assertTrue(orderdetail1.isDeliverable());
	}
	
	@Test
	public void isDeliverable_returns_false_when_not_deliverable() {
		assertFalse(orderdetail2.isDeliverable());
	}
}