package be.vdab.toysforboys.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	private Product product;
	
	@Before
	public void before(){
		product = new Product("testName",20,35);
	}
	
	@Test
	public void deliver() {
		product.deliver(5);		
		assertEquals(15,product.getInStock());
		assertEquals(30,product.getInOrder());
	}
}