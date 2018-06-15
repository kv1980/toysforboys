package be.vdab.toysforboys.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import be.vdab.toysforboys.entities.Country;
import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.entities.Product;
import be.vdab.toysforboys.services.OrderService;
import be.vdab.toysforboys.valueobjects.Adress;
import be.vdab.toysforboys.valueobjects.Orderdetail;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {
	private IndexController controller;
	@Mock 
	private OrderService orderService;
	private Order order1, order2;
	List<Order> unshippedOrders;

	@Before
	public void before() {
		unshippedOrders = new LinkedList<>();
			order1 = new Order(LocalDate.of(2018, 06, 19),new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry"))));
			order1.add(new Orderdetail(new Product("testProduct1"),5,BigDecimal.valueOf(10.01)));
		unshippedOrders.add(order1);
			order2 = new Order(LocalDate.of(2018, 06, 19),new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry"))));
			order2.add(new Orderdetail(new Product("testProduct1"),5,BigDecimal.valueOf(10.01)));
			order2.add(new Orderdetail(new Product("testProduct2"),2,BigDecimal.valueOf(20.02)));
		unshippedOrders.add(order2);
			
		controller = new IndexController(orderService);
		when(orderService.findUnshippedOrders())
			.thenReturn(unshippedOrders);		
	}
	
	@Test
	public void indexController_uses_index_dot_jsp() {
		ModelAndView modelAndView = controller.index();
		assertEquals("index",modelAndView.getViewName());
	}
	
	@Test
	public void indexController_transmits_unshippedOrders() {
		ModelAndView modelAndView = controller.index();
		assertTrue(modelAndView.getModel().containsKey("unshippedOrders"));
	}
}