package be.vdab.toysforboys.valueobjects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.toysforboys.entities.Product;

public class OrderDetailTest {
	private Product product;
	private Orderdetail orderdetail;
	
	@Before
	public void before(){
		product = new Product("testName",20,15);
		orderdetail = new Orderdetail(product,5,BigDecimal.TEN);
	}
	
	@Test
	public void getValue() {
		assertEquals(BigDecimal.valueOf(50),orderdetail.getValue());
	}
}