package be.vdab.toysforboys.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

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
	
	List<Order> unshippedOrders;

	@Before
	public void before() {
		Orderdetail detail1 = new Orderdetail(new Product("testProduct1",6,5),5,BigDecimal.valueOf(10.01));
		Orderdetail detail2 = new Orderdetail(new Product("testProduct2",20,35),2,BigDecimal.valueOf(20.02));
		List<Orderdetail> orderdetails1 = new ArrayList<>();
			orderdetails1.add(detail1);
		List<Orderdetail> orderdetails2 = new ArrayList<>();
			orderdetails1.add(detail1);
			orderdetails1.add(detail2);
		Customer customer = new Customer("testCustomer", new Adress("testStreetAndNumber","testPostalCode","testCity", new Country("testCountry")));
		Order order1 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails1);
		Order order2 = new Order(LocalDate.of(2018, 06, 19),customer,orderdetails2);
		unshippedOrders = new LinkedList<>();
		unshippedOrders.add(order1);
		unshippedOrders.add(order2);
		controller = new IndexController(orderService, new DefaultErrorList());
		when(orderService.findUnshippedOrders())
			.thenReturn(unshippedOrders);
		when(orderService.read(-1L))
			.thenReturn(Optional.empty());
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
	
	@Test 
	public void error_must_be_found_when_order_was_not_found() {
		long[] ids= {-1L};
		RedirectAttributesModelMap redirectAttributes=new RedirectAttributesModelMap();
		String view = controller.afterSetAsShipped(ids,redirectAttributes);
		assertFalse(redirectAttributes.isEmpty());
		String key = "errors";
		assertTrue(redirectAttributes.containsKey(key));
		System.out.println(redirectAttributes.get(key));
		System.out.println(redirectAttributes.get(key).getClass());
	}
}