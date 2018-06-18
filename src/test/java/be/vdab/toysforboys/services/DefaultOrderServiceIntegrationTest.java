package be.vdab.toysforboys.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.repositories.OrderRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(DefaultOrderService.class)
@ComponentScan(basePackageClasses = OrderRepository.class)
@Sql("/insertCountry.sql")
@Sql("/insertCustomer.sql")
@Sql("/insertProduct.sql")
@Sql("/insertOrder.sql")
@Sql("/insertOrderdetails.sql")
public class DefaultOrderServiceIntegrationTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private DefaultOrderService service;
	@Autowired
	private EntityManager manager;
	
	private long idVanOrderA() {
		return super.jdbcTemplate.queryForObject("select id from orders where comments = 'testCommentsA'",Long.class).longValue();
	}
	
	private long idVanOrderB() {
		return super.jdbcTemplate.queryForObject("select id from orders where comments = 'testCommentsB'",Long.class).longValue();
	}
	
	@Test
	public void an_non_existing_order_is_not_found() {
		assertFalse(service.read(-1).isPresent());
	}
	
	@Test
	public void an_existing_order_is_found() {
		assertEquals("testCommentsA",service.read(idVanOrderA()).get().getComments());
	}
	
	@Test
	public void unshippedOrders_are_found() {
		List<Order> testOrders = service.findUnshippedOrders();
		boolean onlyUnshippedFound = true;
		for (Order testOrder : testOrders) {
			Status testStatus = testOrder.getStatus();
			if(testStatus.equals(Status.SHIPPED) || testStatus.equals(Status.CANCELLED)) {
				onlyUnshippedFound = false;
			}
		}
		assertTrue(onlyUnshippedFound);
	}
	
	@Test
	public void order_status_and_date_are_updated_when_order_is_shipped() {
		long id = idVanOrderA();
		service.shipOrderById(id);
		manager.flush();
		assertEquals("SHIPPED",super.jdbcTemplate.queryForObject("select status from orders where id =?",String.class,id));
		assertEquals(LocalDate.now(),super.jdbcTemplate.queryForObject("select shipped from orders where id =?",LocalDate.class,id));
	}
	
	@Test
	public void quantities_inStock_and_inOrder_of_all_orderdetails_are_updated_when_order_is_shipped() {
		long id = idVanOrderA();
		service.shipOrderById(id);
		manager.flush();
		assertEquals(5,super.jdbcTemplate.queryForObject("select inStock from products where name='testProductA'",Long.class).longValue());
		assertEquals(0,super.jdbcTemplate.queryForObject("select inOrder from products where name='testProductA'",Long.class).longValue());
		assertEquals(14,super.jdbcTemplate.queryForObject("select inStock from products where name='testProductB'",Long.class).longValue());
		assertEquals(29,super.jdbcTemplate.queryForObject("select inOrder from products where name='testProductB'",Long.class).longValue());
	}

	@Test
	public void order_status_and_date_is_not_updated_when_order_is_not_shipped() {
		long id = idVanOrderB();
		service.shipOrderById(id);
		manager.flush();
		assertNotEquals("SHIPPED",super.jdbcTemplate.queryForObject("select status from orders where id =?",String.class,id));
		assertNotEquals(LocalDate.now(),super.jdbcTemplate.queryForObject("select shipped from orders where id =?",LocalDate.class,id));
	}
	
	@Test
	public void quantities_inStock_and_inOrder_of_all_orderdetails_are_not_updated_when_order_is_not_shipped() {
		long id = idVanOrderB();
		service.shipOrderById(id);
		manager.flush();
		assertEquals(10,super.jdbcTemplate.queryForObject("select inStock from products where name='testProductA'",Long.class).longValue());
		assertEquals(5,super.jdbcTemplate.queryForObject("select inOrder from products where name='testProductA'",Long.class).longValue());
		assertEquals(20,super.jdbcTemplate.queryForObject("select inStock from products where name='testProductB'",Long.class).longValue());
		assertEquals(35,super.jdbcTemplate.queryForObject("select inOrder from products where name='testProductB'",Long.class).longValue());
	}
}