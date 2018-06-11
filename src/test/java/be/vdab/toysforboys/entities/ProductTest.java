package be.vdab.toysforboys.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	private Product product;
	
	@Before
	public void before(){
		product = new Product("testName",20,15);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void isDeliverableInQuantity_cannot_be_conducted_if_quantity_is_negative() {
		product.isDeliverableInQuantity(-5);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void isDeliverableInQuantity_cannot_be_conducted_if_quantity_is_zero() {
		product.isDeliverableInQuantity(0);
	}
	
	@Test
	public void isDeliverableInQuantity_returns_true_if_quantity_can_be_delivered() {
		assertTrue(product.isDeliverableInQuantity(5));
	}
	
	@Test
	public void isDeliverableInQuantity_returns_false_if_quantity_cannot_be_delivered() {
		assertFalse(product.isDeliverableInQuantity(50));
	}
	
	@Test
	public void subtractInStockAndInOrder() {
		product.subtractInStockAndInOrder(5);		
		assertEquals(15,product.getInStock());
		assertEquals(10,product.getInOrder());
	}
}
