package be.vdab.toysforboys.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.valueobjects.Adress;
import be.vdab.toysforboys.valueobjects.Orderdetail;

public class OrderTest {
	private Order order1, order2;
	
	@Before
	public void before() {
		Orderdetail detail1 = new Orderdetail(new Product("testProduct1",6,5),5,BigDecimal.valueOf(10.01));
		Orderdetail detail2 = new Orderdetail(new Product("testProduct2",1,2),2,BigDecimal.valueOf(20.02));
		List<Orderdetail> orderdetails1 = new ArrayList<>();
			orderdetails1.add(detail1);
		List<Orderdetail>orderdetails2 = new ArrayList<>();
			orderdetails2.add(detail1);
			orderdetails2.add(detail2);
		Customer customer = new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry")));
		order1 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails1);
		order2 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails2);
	}
	
	@Test
	public void order_is_shipped_when_all_orderdetails_are_deliverable() {
		order1.ship();
		assertEquals(Status.SHIPPED,order1.getStatus());
		assertEquals(LocalDate.now(),order1.getShipped());
		Product product1 = order1.getOrderdetails().stream()
				  .filter(detail -> detail.getProduct().getName().equals("testProduct1"))
				  .findFirst()
				  .get()
				  .getProduct();
		assertEquals(1,product1.getInStock());
		assertEquals(0,product1.getInOrder());
	}
	
	@Test
	public void order_is_not_shipped_when_one_or_more_orderdetails_are_not_deliverable() {
		order2.ship();
		assertNotEquals(Status.SHIPPED,order2.getStatus());
		assertNotEquals(LocalDate.now(),order2.getShipped());
		Product product1 = order2.getOrderdetails().stream()
				  .filter(detail -> detail.getProduct().getName().equals("testProduct1"))
				  .findFirst()
				  .get()
				  .getProduct();
		assertEquals(6,product1.getInStock());
		assertEquals(5,product1.getInOrder());
		Product product2 = order2.getOrderdetails().stream()
				  .filter(detail -> detail.getProduct().getName().equals("testProduct2"))
				  .findFirst()
				  .get()
				  .getProduct();
		assertEquals(1,product2.getInStock());
		assertEquals(2,product2.getInOrder());	
	}
		
	@Test
	public void totalValue_counts_the_values_of_all_orderdetails() {
		assertEquals(BigDecimal.valueOf(90.09),order2.getValue());
	}
}