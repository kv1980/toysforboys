package be.vdab.toysforboys.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.toysforboys.entities.Country;
import be.vdab.toysforboys.entities.Customer;
import be.vdab.toysforboys.entities.Order;
import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.valueobjects.Adress;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertCountry.sql")
@Sql("/insertCustomer.sql")
@Sql("/insertProduct.sql")
@Sql("/insertOrder.sql")
@Sql("/insertOrderdetails.sql")
@Import(JpaOrderRepository.class)
public class JpaOrderRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	JpaOrderRepository repository;
	@Autowired
	EntityManager manager;
	
	private long idOfTestOrder() {
		return super.jdbcTemplate.queryForObject("select id from orders where ordered ='2000-01-01'",Long.class);
	}
	
	@Test
	public void read_may_not_find_an_non_existing_order() {
		assertFalse(repository.read(-1L).isPresent());
	}
	
	@Test
	public void read_has_to_find_an_existing_order() {
		assertTrue(repository.read(idOfTestOrder()).isPresent());
	}
	
	@Test
	public void read_has_to_find_data_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		assertEquals(LocalDate.of(2000, 01, 01),order.getOrdered());
		assertEquals(LocalDate.of(2001, 01, 01),order.getRequired());
	}
	
	@Test
	public void read_has_to_find_status_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		assertEquals(Status.PROCESSING,order.getStatus());
	}
	
	@Test
	public void read_has_to_find_comments_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		assertEquals("testComments",order.getComments());
	}
	
	@Test
	public void read_has_to_find_customer_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		Customer customer = order.getCustomer();
		assertEquals("testName",customer.getName());
	}
	
	@Test
	public void read_has_to_find_adress_of_customer_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		Customer customer = order.getCustomer();
		Adress adress = customer.getAdress();
		assertEquals("testStreetAndNumber",adress.getStreetAndNumber());
		assertEquals("testPostalCode",adress.getPostalCode());
		assertEquals("testCity",adress.getCity());
		assertEquals("testState",adress.getState());
	}
	
	@Test
	public void read_has_to_find_country_of_adress_of_customer_of_an_existing_order() {
		Order order = repository.read(idOfTestOrder()).get();
		Customer customer = order.getCustomer();
		Adress adress = customer.getAdress();
		Country country = adress.getCountry();
		assertEquals("testCountry",country.getName());
	}
	
	
	
}