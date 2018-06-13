package be.vdab.toysforboys.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
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
	private Product product1,product2;
	private Orderdetail detail1,detail2;
	private Customer customer;
	private Order order1,order2;	
	
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
		when(repository.read(-1)).thenReturn(Optional.empty());
		when(repository.read(1)).thenReturn(Optional.of(order1));
		when(repository.read(2)).thenReturn(Optional.of(order2));
		service = new DefaultOrderService(repository);
	}
	
	@Test
	public void updateOrderById_updates_order_status_and_date_when_order_can_be_shipped() {
		assertTrue(service.shipOrderById(1));
		assertEquals(Status.SHIPPED,order1.getStatus());
		assertEquals(LocalDate.now(),order1.getShipped());
		verify(repository).read(1);
	}
	
	@Test
	public void updateOrderById_updates_productquantities_inStock_and_inOrder_when_order_can_be_shipped() {
		assertTrue(service.shipOrderById(1));
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
	public void updateOrderById_does_not_update_order_status_and_date_when_order_cannot_be_shipped() {
		assertFalse(service.shipOrderById(2));
		assertNotEquals(Status.SHIPPED,order2.getStatus());
		assertNotEquals(LocalDate.now(),order2.getShipped());
		verify(repository).read(2);
	}
	
	@Test
	public void updateOrderById_does_not_update_quantities_inStock_and_inOrder_of_any_ordered_product_when_order_cannot_be_shipped() {
		assertFalse(service.shipOrderById(2));
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
	
	@Test (expected = OrderNotFoundException.class)
	public void updateOrderById_cannot_be_conducted_of_an_non_existing_order() {
		service.shipOrderById(-1);
		verify(repository).read(-1);
	}
}