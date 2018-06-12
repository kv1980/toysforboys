package be.vdab.toysforboys.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.valueobjects.Adress;
import be.vdab.toysforboys.valueobjects.Orderdetail;

public class OrderTest {
	private Product product1,product2;
	private Orderdetail detail1,detail2;
	private Order order1, order2;
	private Customer customer;
	
	@Before
	public void before() {
		product1 = new Product("testProduct1");
		product1.addInStock(6);
		product1.addInOrder(5);
		product2 = new Product("testProduct2");
		product2.addInStock(1);
		product2.addInOrder(2);
		detail1 = new Orderdetail(product1,5,BigDecimal.valueOf(10.01));
		detail2 = new Orderdetail(product2,2,BigDecimal.valueOf(20.02));
		customer = new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry")));
		order1 = new Order(LocalDate.of(2018, 06, 19),customer);
		order1.add(detail1);
		order2 = new Order(LocalDate.of(2018, 06, 19),customer);
		order2.add(detail1);
		order2.add(detail2);
	}
	
	@Test
	public void isDeliverable_returns_true_when_all_orderdetails_are_deliverable() {
		assertTrue(order1.isDeliverable());
	}
	
	@Test
	public void isDeliverable_returns_false_when_one_or_more_orderdetails_are_not_deliverable() {
		assertFalse(order2.isDeliverable());
	}
	
	@Test 
	public void setStatusOnSHIPPED() {
		assertEquals(Status.PROCESSING,order1.getStatus());
		order1.setStatusOnSHIPPED();
		assertEquals(Status.SHIPPED,order1.getStatus());
	}
	
	@Test 
	public void setShippedDateOnToday() {
		order1.setShippedDateOnToday();
		assertEquals(LocalDate.now(),order1.getShipped());
	}
	
	@Test
	public void getTotalValue_counts_the_values_of_the_orderdetails() {
		assertEquals(BigDecimal.valueOf(90.09),order2.getTotalValue());
	}
}