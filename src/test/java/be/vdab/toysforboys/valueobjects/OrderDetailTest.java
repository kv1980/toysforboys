package be.vdab.toysforboys.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.entities.Product;

public class OrderDetailTest {
	private Product product1, product2;
	private Orderdetail orderdetail1, orderdetail1copy, orderdetail2;
	
	@Before
	public void before(){
		product1 = new Product("testName1",20L,35L);
		product2 = new Product("testName2",0L,0L);
		orderdetail1 = new Orderdetail(product1,5,BigDecimal.TEN);
		orderdetail1copy = new Orderdetail(product1,5,BigDecimal.TEN);
		orderdetail2 = new Orderdetail(product2,30,BigDecimal.TEN);
	}
	
	@Test
	public void getValue() {
		assertEquals(BigDecimal.valueOf(50),orderdetail1.getValue());
	}
	
	@Test
	public void orderdetail_is_deliverable_when_ordered_quantity_is_less_or_even_then_productquantity_in_stock() {
		assertTrue(orderdetail1.isDeliverable());
	}
	
	@Test
	public void orderdetail_is_not_deliverable_when_ordered_quantity_is_more_then_productquantity_in_stock() {
		assertFalse(orderdetail2.isDeliverable());
	}
	
	@Test
	public void hashCode_of_two_equal_orderdetails_are_equal() {
		assertEquals(orderdetail1.hashCode(),orderdetail1copy.hashCode());
	}
	
	@Test
	public void hashCode_of_two_different_orderdetails_are_not_equal() {
		assertNotEquals(orderdetail1.hashCode(),orderdetail2.hashCode());
	}
	
	@Test
	public void two_orderdetails_are_equal_when_productname_is_the_same() {
		assertTrue(orderdetail1.equals(orderdetail1copy));
	}
	
	@Test
	public void two_orderdetails_are_not_equal_when_productname_is_different() {
		assertFalse(orderdetail1.equals(orderdetail2));
	}
	
	@Test
	public void an_orderdetail_cannot_be_equal_to_null() {
		assertFalse(orderdetail1.equals(null));
	}
}