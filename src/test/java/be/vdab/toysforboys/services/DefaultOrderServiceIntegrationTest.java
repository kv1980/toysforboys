package be.vdab.toysforboys.services;

import javax.persistence.EntityManager;

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

import be.vdab.fietsacademy.services.DefaultDocentService;
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
	
	
	
	
	
	
	
}
