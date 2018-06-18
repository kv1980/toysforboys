package be.vdab.toysforboys.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import be.vdab.toysforboys.entities.Country;
import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.entities.Product;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.exceptions.OrderNotFoundException;
import be.vdab.toysforboys.repositories.OrderRepository;
import be.vdab.toysforboys.valueobjects.Adress;
import be.vdab.toysforboys.valueobjects.Orderdetail;

@RunWith(MockitoJUnitRunner.class)
public class DefaultOrderServiceUnitTest {
	private DefaultOrderService service;
	@Mock
	private OrderRepository repository;
	private Orderdetail detail1,detail2;
	private Customer customer;
	private Order order1,order2;
	private List<Order> unshippableOrders;
	
	@Before
	public void before() {
		detail1 = new Orderdetail(new Product("testProduct1",6,5),5,BigDecimal.valueOf(10.01));
		detail2 = new Orderdetail(new Product("testProduct2",1,2),2,BigDecimal.valueOf(20.02));
		List<Orderdetail> orderdetails1 = new ArrayList<>();
			orderdetails1.add(detail1);
		List<Orderdetail> orderdetails2 = new ArrayList<>();
			orderdetails2.add(detail1);
			orderdetails2.add(detail2);
		customer = new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry")));
		order1 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails1);
		order2 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails2);
		when(repository.read(-1)).thenReturn(Optional.empty());
		when(repository.read(1)).thenReturn(Optional.of(order1));
		when(repository.read(2)).thenReturn(Optional.of(order2));
		
		unshippableOrders = new LinkedList<>();
		unshippableOrders.add(order2);
		when(repository.findUnshippedOrders()).thenReturn(unshippableOrders);
		service = new DefaultOrderService(repository);
	}
	
	@Test
	public void an_non_existing_order_is_not_found() {
		assertFalse(service.read(-1).isPresent());
		verify(repository).read(-1);
	}
	
	@Test
	public void an_existing_order_is_found() {
		assertTrue(service.read(1).isPresent());
		verify(repository).read(1);
	}
	
	@Test
	public void unshipped_orders_are_found() {
		List<Order> testOrders = service.findUnshippedOrders();
		assertEquals(1,testOrders.size());
		assertTrue(testOrders.contains(order2));
		verify(repository).findUnshippedOrders();
	}
	
	@Test
	public void order_status_and_date_are_updated_when_order_is_shipped() {
		service.shipOrderById(1);
		assertEquals(Status.SHIPPED,order1.getStatus());
		assertEquals(LocalDate.now(),order1.getShipped());
		verify(repository).read(1);
	}
	
	@Test
	public void quantities_inStock_and_inOrder_of_all_orderdetails_are_updated_when_order_can_be_shipped() {
		service.shipOrderById(1);
		Product productA = order1.getOrderdetails().stream()
												  .filter(detail -> detail.getProduct().getName().equals("testProduct1"))
												  .findFirst()
												  .get()
												  .getProduct();
		assertEquals(1,productA.getInStock());
		assertEquals(0,productA.getInOrder());
		verify(repository).read(1);
	}

	@Test
	public void order_status_and_date_are_not_updated_when_order_cannot_be_shipped() {
		service.shipOrderById(2);
		assertNotEquals(Status.SHIPPED,order2.getStatus());
		assertNotEquals(LocalDate.now(),order2.getShipped());
		verify(repository).read(2);
	}
	
	@Test
	public void quantities_inStock_and_inOrder_of_all_orderdetatils_are_not_updated_when_order_cannot_be_shipped() {
		service.shipOrderById(2);
		Product productA = order2.getOrderdetails().stream()
												  .filter(detail -> detail.getProduct().getName().equals("testProduct1"))
												  .findFirst()
												  .get()
												  .getProduct();		
		assertEquals(6,productA.getInStock());
		assertEquals(5,productA.getInOrder());
		Product productB = order2.getOrderdetails().stream()
				  								   .filter(detail -> detail.getProduct().getName().equals("testProduct2"))
				  								   .findFirst()
				  								   .get()
				  								   .getProduct();
		assertEquals(1,productB.getInStock());
		assertEquals(2,productB.getInOrder());
		verify(repository).read(2);
	}
}